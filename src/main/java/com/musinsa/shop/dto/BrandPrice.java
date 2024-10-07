package com.musinsa.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandPrice {
    private String brand;
    private String price;

    public BrandPrice(String brand, String price) {
        this.brand = brand;
        this.price = price;
    }
}