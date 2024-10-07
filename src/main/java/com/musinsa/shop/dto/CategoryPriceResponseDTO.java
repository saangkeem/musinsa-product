package com.musinsa.shop.dto;

import lombok.Getter;

import java.util.List;

@Getter

public class CategoryPriceResponseDTO {
    private List<CategoryPrice> products;
    private String totalAmount;

    // 기본 생성자
    public CategoryPriceResponseDTO(List<CategoryPrice> products, String totalAmount) {

        this.products = products;
        this.totalAmount = totalAmount;
    }

}