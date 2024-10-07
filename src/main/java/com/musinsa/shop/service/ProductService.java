package com.musinsa.shop.service;

import com.musinsa.shop.dto.*;
import com.musinsa.shop.entity.Brand;
import com.musinsa.shop.entity.BrandProductsAudit;
import com.musinsa.shop.entity.Category;
import com.musinsa.shop.entity.Product;
import com.musinsa.shop.repository.BrandProductAuditRepository;
import com.musinsa.shop.repository.BrandRepository;
import com.musinsa.shop.repository.CategoryRepository;
import com.musinsa.shop.repository.ProductRepository;
import com.musinsa.shop.util.PriceFormatter;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final BrandProductAuditRepository auditRepository;


    // 생성자 주입
    public ProductService(ProductRepository productRepository, BrandRepository brandRepository, CategoryRepository categoryRepository, BrandProductAuditRepository auditRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.auditRepository = auditRepository;
        this.categoryRepository = categoryRepository;
    }

    //구현 1) - 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
    public CategoryPriceResponseDTO getLowestPriceByCategory() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new IllegalArgumentException("가능한 상품이 없습니다.");
        }

        Map<String, Product> lowestPriceProducts = products.stream()
                .collect(Collectors.groupingBy(
                        product -> product.getCategory().getName(),
                        Collectors.collectingAndThen(
                                Collectors.minBy((p1, p2) -> p1.getPrice().compareTo(p2.getPrice())),
                                opt -> opt.orElseThrow(() -> new IllegalStateException("최저가를 찾을 수 없습니다."))
                        )
                ));

        List<CategoryPrice> responseList = lowestPriceProducts.entrySet().stream()
                .map(entry -> new CategoryPrice(
                        entry.getKey(),
                        entry.getValue().getBrand().getName(),
                        PriceFormatter.formatPrice(entry.getValue().getPrice())
                ))
                .collect(Collectors.toList());

        BigDecimal totalAmount = lowestPriceProducts.values().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CategoryPriceResponseDTO(responseList, PriceFormatter.formatPrice(totalAmount));
    }

    //구현 2) - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
    public SingleBrandPriceResponseDTO getLowestPriceForSingleBrand() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new IllegalArgumentException("찾는 상품이 없습니다.");
        }

        Map<String, Map<String, Product>> brandToCategoryPrices = products.stream()
                .collect(Collectors.groupingBy(
                        product -> product.getBrand().getName(),
                        Collectors.toMap(
                                product -> product.getCategory().getName(),
                                product -> product,
                                (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()) < 0 ? p1 : p2
                        )
                ));

        Map<String, BigDecimal> brandTotalPrices = brandToCategoryPrices.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().values().stream()
                                .map(Product::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ));

        Map.Entry<String, BigDecimal> lowestPriceBrandEntry = brandTotalPrices.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalStateException("찾는 브랜드가 없습니다."));

        String lowestPriceBrand = lowestPriceBrandEntry.getKey();
        BigDecimal totalAmount = lowestPriceBrandEntry.getValue();
        Map<String, Product> lowestPriceBrandCategories = brandToCategoryPrices.get(lowestPriceBrand);

        List<CategoryPrice> categoryPriceList = lowestPriceBrandCategories.entrySet().stream()
                .map(entry -> new CategoryPrice(
                        entry.getKey(),
                        PriceFormatter.formatPrice(entry.getValue().getPrice())
                ))
                .collect(Collectors.toList());

        return new SingleBrandPriceResponseDTO(
                lowestPriceBrand,
                categoryPriceList,
                PriceFormatter.formatPrice(totalAmount)
        );
    }


    //구현 3) - 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
    public CategoryPriceRangeDTO getPriceRangeByCategory(String categoryName) {
        List<Product> products = productRepository.findAll();
        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getCategory().getName().equalsIgnoreCase(categoryName))
                .toList();

        if (filteredProducts.isEmpty()) {
            throw new IllegalArgumentException("해당 카테고리 존재 하지 않음 : " + categoryName);
        }

        Product minPriceProduct = filteredProducts.stream()
                .min((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()))
                .orElseThrow(() -> new IllegalStateException("최저가 상품을 찾을 수 없습니다."));

        Product maxPriceProduct = filteredProducts.stream()
                .max((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()))
                .orElseThrow(() -> new IllegalStateException("최고가 상품을 찾을 수 없습니다."));

        return new CategoryPriceRangeDTO(
                categoryName,
                new BrandPrice(minPriceProduct.getBrand().getName(), PriceFormatter.formatPrice(minPriceProduct.getPrice())),
                new BrandPrice(maxPriceProduct.getBrand().getName(), PriceFormatter.formatPrice(maxPriceProduct.getPrice()))
        );
    }


    // 상품 추가 (트랜잭션 적용)
    @Transactional
    public Product addProduct(ProductDTO productDTO) {
        Brand brand = brandRepository.findById(productDTO.getBrand().getId())
                .orElseThrow(() -> new IllegalArgumentException("브랜드 정보 없음"));

        Category category = categoryRepository.findById(productDTO.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리 정보 없음"));

        Product product = new Product();
        product.setPrice(productDTO.getPrice());
        product.setBrand(brand);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        saveAuditLog(savedProduct.getId(), brand.getId(), category.getId(), null, productDTO.getPrice(), "ADD");
        return savedProduct;
    }

    // 상품 업데이트 (트랜잭션 적용)
    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 아이디를 찾을 수 없습니다. : " + id));

        BigDecimal oldPrice = existingProduct.getPrice();
        existingProduct.setPrice(productDTO.getPrice());

        if (productDTO.getBrand() != null && productDTO.getBrand().getId() != null) {
            Brand brand = brandRepository.findById(productDTO.getBrand().getId())
                    .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));
            existingProduct.setBrand(brand);
        }

        if (productDTO.getCategory() != null && productDTO.getCategory().getId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategory().getId())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
            existingProduct.setCategory(category);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        saveAuditLog(id, updatedProduct.getBrand().getId(), updatedProduct.getCategory().getId(), oldPrice, productDTO.getPrice(), "UPDATE");

        return updatedProduct;
    }


    // 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 아이디를 찾을 수 없습니다. : " + id));

        productRepository.deleteById(id);
        saveAuditLog(id, product.getBrand().getId(), product.getCategory().getId(), product.getPrice(), null, "DELETE");
    }

    // 히스토리 저장 메서드
    protected void saveAuditLog(Long productId, Long brandId, Long categoryId, BigDecimal oldPrice, BigDecimal newPrice, String changeType) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("로그 저장 실패: 브랜드 아이디 없음: " + brandId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("로그 저장 실패: 카테고리 아이디 없음: " + categoryId));

        BrandProductsAudit audit = new BrandProductsAudit();
        audit.setProductId(productId);
        audit.setBrand(brand);
        audit.setCategory(category);
        audit.setOldPrice(oldPrice);
        audit.setNewPrice(newPrice);
        audit.setChangeType(changeType);
        audit.setChangeDate(LocalDateTime.now());

        auditRepository.save(audit);
    }


}
