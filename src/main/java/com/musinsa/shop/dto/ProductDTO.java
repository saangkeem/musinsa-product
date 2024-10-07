package com.musinsa.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {
    private String name;
    private BigDecimal price;
    private BrandDTO brand;
    private CategoryDTO category;

}
