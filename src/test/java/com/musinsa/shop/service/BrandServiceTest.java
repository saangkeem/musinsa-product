package com.musinsa.shop.service;

import com.musinsa.shop.dto.BrandDTO;
import com.musinsa.shop.entity.Brand;
import com.musinsa.shop.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBrand() {
        BrandDTO brandDTO = new BrandDTO("Test Brand");

        Brand brand = new Brand();
        brand.setName(brandDTO.getName());

        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        Brand savedBrand = brandService.addBrand(brandDTO);

        assertNotNull(savedBrand);
        assertEquals("Test Brand", savedBrand.getName());
        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    @Test
    void testUpdateBrand_Success() {
        Long id = 1L;
        BrandDTO brandDTO = new BrandDTO("Updated Brand");

        Brand existingBrand = new Brand();
        existingBrand.setName("Old Brand");

        when(brandRepository.findById(id)).thenReturn(Optional.of(existingBrand));
        when(brandRepository.save(any(Brand.class))).thenReturn(existingBrand);

        Brand updatedBrand = brandService.updateBrand(id, brandDTO);

        assertNotNull(updatedBrand);
        assertEquals("Updated Brand", updatedBrand.getName());
        verify(brandRepository, times(1)).findById(id);
        verify(brandRepository, times(1)).save(existingBrand);
    }

    @Test
    void testUpdateBrand_NotFound() {
        Long id = 1L;
        BrandDTO brandDTO = new BrandDTO("adadfff");

        when(brandRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            brandService.updateBrand(id, brandDTO);
        });

        verify(brandRepository, times(1)).findById(id);
        verify(brandRepository, never()).save(any(Brand.class));
    }

    @Test
    void testDeleteBrand_Success() {
        Long id = 1L;
        Brand brand = new Brand();
        when(brandRepository.findById(id)).thenReturn(Optional.of(brand));

        brandService.deleteBrand(id);

        verify(brandRepository, times(1)).findById(id);
        verify(brandRepository, times(1)).delete(brand);
    }

    @Test
    void testDeleteBrand_NotFound() {
        Long id = 1L;
        when(brandRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            brandService.deleteBrand(id);
        });

        verify(brandRepository, times(1)).findById(id);
        verify(brandRepository, never()).delete(any(Brand.class));
    }
}
