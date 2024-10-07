package com.musinsa.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SingleBrandPriceResponseDTO {
    private String brand;
    private List<CategoryPrice> categories;
    private String totalAmount;

    // 기본 생성자
    public SingleBrandPriceResponseDTO(String brand, List<CategoryPrice> categories, String totalAmount) {
        this.brand = brand;
        this.categories = categories;
        this.totalAmount = totalAmount;
    }

}