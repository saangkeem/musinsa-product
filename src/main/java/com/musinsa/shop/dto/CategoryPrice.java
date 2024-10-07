package com.musinsa.shop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class CategoryPrice {
    private String category;
    private String brand;
    private String price;


    // 기본 생성자
    public CategoryPrice(String category, String brand, String price) {
        this.category = category;
        this.brand = brand;
        this.price = price;
    }

    public CategoryPrice(String category, String price) {
        this.category = category;
        this.price = price;
    }


}