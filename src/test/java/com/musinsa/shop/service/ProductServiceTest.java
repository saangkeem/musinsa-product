package com.musinsa.shop.service;

import com.musinsa.shop.dto.BrandDTO;
import com.musinsa.shop.dto.CategoryDTO;
import com.musinsa.shop.dto.ProductDTO;
import com.musinsa.shop.entity.Brand;
import com.musinsa.shop.entity.Category;
import com.musinsa.shop.entity.Product;
import com.musinsa.shop.repository.BrandProductAuditRepository;
import com.musinsa.shop.repository.BrandRepository;
import com.musinsa.shop.repository.CategoryRepository;
import com.musinsa.shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandProductAuditRepository auditRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(new BigDecimal("1000"));

        // BrandDTO 설정
        BrandDTO brandDTO = new BrandDTO(1L, "test");
        productDTO.setBrand(brandDTO);  // BrandDTO 설정

        // CategoryDTO 설정
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        productDTO.setCategory(categoryDTO);  // CategoryDTO 설정

        // Mock brandRepository 및 categoryRepository
        Brand brand = new Brand();
        brand.setId(1L);
        Category category = new Category();
        category.setId(1L);

        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Product savedProduct = productService.addProduct(productDTO);

        // Assert
        assertNotNull(savedProduct);
        assertEquals(productDTO.getPrice(), savedProduct.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        Long id = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(new BigDecimal("1500"));

        Brand brand = new Brand();
        brand.setId(1L);

        Category category = new Category();
        category.setId(1L);

        Product existingProduct = new Product();
        existingProduct.setId(id);
        existingProduct.setPrice(new BigDecimal("1000"));
        existingProduct.setBrand(brand);  // 브랜드 설정
        existingProduct.setCategory(category);  // 카테고리 설정

        // Mock 설정: brandRepository에서 brand 찾기
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));  // 브랜드 조회 성공하도록 설정
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));  // 카테고리 조회 성공하도록 설정

        // Act
        Product updatedProduct = productService.updateProduct(id, productDTO);

        // Assert
        assertEquals(productDTO.getPrice(), updatedProduct.getPrice());
        verify(productRepository, times(1)).save(existingProduct);
        verify(brandRepository, times(1)).findById(1L);  // 브랜드 조회 검증
        verify(categoryRepository, times(1)).findById(1L);  // 카테고리 조회 검증
        verify(auditRepository, times(1)).save(any());  // Audit 로그 저장 호출 검증
    }



    @Test
    void testUpdateProduct_NotFound() {
        // Arrange
        Long id = 1L;
        ProductDTO productDTO = new ProductDTO();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(id, productDTO);
        });

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        Long id = 1L;

        Brand brand = new Brand();
        brand.setId(1L);  // 브랜드 설정

        Category category = new Category();
        category.setId(1L);  // 카테고리 설정

        Product product = new Product();
        product.setId(id);
        product.setBrand(brand);  // Brand 설정
        product.setCategory(category);  // Category 설정

        // Mock 설정: brandRepository에서 brand 찾기
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));  // 브랜드 조회 성공하도록 설정
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));  // 카테고리 조회 성공하도록 설정

        // Act
        productService.deleteProduct(id);

        // Assert
        verify(productRepository, times(1)).deleteById(id);
        verify(auditRepository, times(1)).save(any());  // Audit 로그 저장 호출 검증
    }



    @Test
    void testDeleteProduct_NotFound() {
        // Arrange
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            productService.deleteProduct(id);
        });

        verify(productRepository, never()).deleteById(id);
    }
}
