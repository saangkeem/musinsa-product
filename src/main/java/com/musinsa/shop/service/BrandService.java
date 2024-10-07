package com.musinsa.shop.service;

import com.musinsa.shop.dto.BrandDTO;
import com.musinsa.shop.entity.Brand;
import com.musinsa.shop.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    // 생성자 주입
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    // 브랜드 추가
    @Transactional
    public Brand addBrand(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        return brandRepository.save(brand);
    }

    // 브랜드 업데이트
    @Transactional
    public Brand updateBrand(Long id, BrandDTO brandDTO) {

        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("브랜드를 찾을 수 없습니다. : " + id));

        // 브랜드 이름 업데이트
        existingBrand.setName(brandDTO.getName());
        return brandRepository.save(existingBrand);
    }

    // 브랜드 삭제
    @Transactional
    public void deleteBrand(Long id) {

        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("브랜드를 찾을 수 없습니다. : " + id));

        brandRepository.delete(existingBrand);
    }
}
