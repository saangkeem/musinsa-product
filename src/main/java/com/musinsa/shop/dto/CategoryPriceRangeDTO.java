package com.musinsa.shop.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryPriceRangeDTO {
    private String category;
    private BrandPrice minPrice;
    private BrandPrice maxPrice;

    public CategoryPriceRangeDTO(String category, BrandPrice minPrice, BrandPrice maxPrice) {
        this.category = category;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
