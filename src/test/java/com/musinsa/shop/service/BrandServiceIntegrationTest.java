package com.musinsa.shop.service;


import com.musinsa.shop.dto.BrandDTO;
import com.musinsa.shop.entity.Brand;
import com.musinsa.shop.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BrandServiceIntegrationTest {

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    void testAddBrand() {
        BrandDTO brandDTO = new BrandDTO("New Brand");


        Brand savedBrand = brandService.addBrand(brandDTO);

        assertNotNull(savedBrand.getId());
        assertEquals("New Brand", savedBrand.getName());

        Optional<Brand> foundBrand = brandRepository.findById(savedBrand.getId());
        assertTrue(foundBrand.isPresent());
        assertEquals("New Brand", foundBrand.get().getName());
    }

    @Test
    void testUpdateBrand() {
        // 먼저 브랜드 추가
        BrandDTO brandDTO = new BrandDTO("Initial Brand");

        Brand savedBrand = brandService.addBrand(brandDTO);

        // 브랜드 업데이트
        BrandDTO updateDTO = new BrandDTO("Initial Brand");
        updateDTO.setName("Updated Brand");
        Brand updatedBrand = brandService.updateBrand(savedBrand.getId(), updateDTO);

        assertEquals("Updated Brand", updatedBrand.getName());

        Optional<Brand> foundBrand = brandRepository.findById(savedBrand.getId());
        assertTrue(foundBrand.isPresent());
        assertEquals("Updated Brand", foundBrand.get().getName());
    }

    @Test
    void testDeleteBrand() {
        // 브랜드 추가
        BrandDTO brandDTO = new BrandDTO("Brand To Delete");

        Brand savedBrand = brandService.addBrand(brandDTO);

        // 브랜드 삭제
        brandService.deleteBrand(savedBrand.getId());

        Optional<Brand> deletedBrand = brandRepository.findById(savedBrand.getId());
        assertFalse(deletedBrand.isPresent());
    }

    @Test
    void testDeleteBrand_NotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            brandService.deleteBrand(999L); // 존재하지 않는 ID
        });
    }
}
