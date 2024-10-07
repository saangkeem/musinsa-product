package com.musinsa.shop.controller;

import com.musinsa.shop.dto.ApiResponseDTO;
import com.musinsa.shop.dto.BrandDTO;
import com.musinsa.shop.dto.ErrorResponseDTO;
import com.musinsa.shop.entity.Brand;
import com.musinsa.shop.service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/brands")
public class BrandController {


    private BrandService brandService;

    // 생성자 주입
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }


    // 브랜드 추가
    @PostMapping("/add")
    public ResponseEntity<?> addBrand(@RequestBody BrandDTO brandDTO) {
        try {
            Brand savedBrand = brandService.addBrand(brandDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO("브랜드 추가 성공", savedBrand));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("브랜드 저장 실패", e.getMessage()));
        }
    }

    // 브랜드 업데이트 API
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody BrandDTO brandDTO) {
        try {
            Brand updatedBrand = brandService.updateBrand(id, brandDTO);
            return ResponseEntity.ok(new ApiResponseDTO("브랜드 변경 성공", updatedBrand));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("브랜드 변경 실패", e.getMessage()));
        }
    }


    // 브랜드 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        try {
            brandService.deleteBrand(id);
            return ResponseEntity.ok(new ApiResponseDTO("브랜드 삭제 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("브랜드 삭제 실패", e.getMessage()));
        }
    }

}



