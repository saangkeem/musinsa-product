package com.musinsa.shop.controller;


import com.musinsa.shop.dto.*;
import com.musinsa.shop.entity.Product;
import com.musinsa.shop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/products")
public class ProductController {


    private ProductService productService;

    // 생성자 주입
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/lowest-price")
    public ResponseEntity<?> getLowestPriceByCategory() {
        try {
            CategoryPriceResponseDTO response = productService.getLowestPriceByCategory();
            return ResponseEntity.ok(response);
        } catch (Exception e) {

            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    "오류가 발생 했습니다.",
                    e.getMessage()
            );
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/lowest-price-brand")
    public ResponseEntity<?> getLowestPriceForSingleBrand() {
        try {
            SingleBrandPriceResponseDTO response = productService.getLowestPriceForSingleBrand();
            return ResponseEntity.ok(response);
        } catch (Exception e) {

            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    "오류가 발생 했습니다.",
                    e.getMessage()
            );
            return ResponseEntity.status(500).body(errorResponse);
        }
    }


    @GetMapping("/price-range-by-category")
    public ResponseEntity<?> getPriceRangeByCategory(@RequestParam String category) {
        try {
            CategoryPriceRangeDTO response = productService.getPriceRangeByCategory(category);
            return ResponseEntity.ok(response);
        } catch (Exception e) {

            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    "오류가 발생 했습니다.",
                    e.getMessage()
            );
            return ResponseEntity.status(500).body(errorResponse);
        }
    }


    // 상품 추가
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product savedProduct = productService.addProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO("상품 저장 성공", savedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("상품 저장 실패", e.getMessage()));
        }

    }

    // 상품 업데이트
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(new ApiResponseDTO("상품 업데이트 성공", updatedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("상품 업데이트 실패", e.getMessage()));
        }

    }

    // 상품 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponseDTO("상품 삭제 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("상품 삭제 실패", e.getMessage()));
        }
    }


}








