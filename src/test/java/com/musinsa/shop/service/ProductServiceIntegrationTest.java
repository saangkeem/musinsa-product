package com.musinsa.shop.service;

import com.musinsa.shop.dto.BrandDTO;
import com.musinsa.shop.dto.CategoryDTO;
import com.musinsa.shop.dto.ProductDTO;
import com.musinsa.shop.entity.Brand;
import com.musinsa.shop.entity.Category;
import com.musinsa.shop.entity.Product;
import com.musinsa.shop.repository.BrandRepository;
import com.musinsa.shop.repository.CategoryRepository;
import com.musinsa.shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Brand savedBrand;
    private Category savedCategory;

    @BeforeEach
    void setup() {
        // Arrange: 기본 브랜드와 카테고리 추가
        Brand brand = new Brand();
        brand.setName("Test Brand");
        savedBrand = brandRepository.save(brand);

        Category category = new Category();
        category.setName("Test Category");
        savedCategory = categoryRepository.save(category);
    }

    @Test
    void testAddProduct() {
        // Arrange

        BrandDTO brandDTO = new BrandDTO(savedBrand.getId(), savedBrand.getName());
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(savedCategory.getId());
        categoryDTO.setName(savedCategory.getName());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(new BigDecimal("1000"));
        productDTO.setBrand(brandDTO);
        productDTO.setCategory(categoryDTO);

        // Act
        Product savedProduct = productService.addProduct(productDTO);

        // Assert
        assertNotNull(savedProduct);
        assertEquals(productDTO.getPrice(), savedProduct.getPrice());
        assertEquals(savedBrand, savedProduct.getBrand());
        assertEquals(savedCategory, savedProduct.getCategory());
    }

    @Test
    void testUpdateProduct() {
        // Arrange

        BrandDTO brandDTO = new BrandDTO(savedBrand.getId(), savedBrand.getName());
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(savedCategory.getId());
        categoryDTO.setName(savedCategory.getName());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(new BigDecimal("1000"));
        productDTO.setBrand(brandDTO);
        productDTO.setCategory(categoryDTO);

        Product savedProduct = productService.addProduct(productDTO);

        // Act: 가격 업데이트
        productDTO.setPrice(new BigDecimal("1500"));
        Product updatedProduct = productService.updateProduct(savedProduct.getId(), productDTO);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals(new BigDecimal("1500"), updatedProduct.getPrice());
    }

    @Test
    void testDeleteProduct() {
        // Arrange

        BrandDTO brandDTO = new BrandDTO(savedBrand.getId(), savedBrand.getName());
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(savedCategory.getId());
        categoryDTO.setName(savedCategory.getName());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(new BigDecimal("1000"));
        productDTO.setBrand(brandDTO);
        productDTO.setCategory(categoryDTO);

        Product savedProduct = productService.addProduct(productDTO);

        // Act: 상품 삭제
        productService.deleteProduct(savedProduct.getId());

        // Assert: 삭제 확인
        Optional<Product> deletedProduct = productRepository.findById(savedProduct.getId());
        assertFalse(deletedProduct.isPresent());
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Arrange


        BrandDTO brandDTO = new BrandDTO(savedBrand.getId(), savedBrand.getName());
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(savedCategory.getId());
        categoryDTO.setName(savedCategory.getName());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(new BigDecimal("1000"));
        productDTO.setBrand(brandDTO);
        productDTO.setCategory(categoryDTO);

        // Act & Assert: 존재하지 않는 상품 업데이트 시도
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(999L, productDTO);
        });
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Act & Assert: 존재하지 않는 상품 삭제 시도
        assertThrows(IllegalArgumentException.class, () -> {
            productService.deleteProduct(999L);
        });
    }
}
