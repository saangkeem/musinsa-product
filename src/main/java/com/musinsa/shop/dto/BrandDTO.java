package com.musinsa.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandDTO {
    private Long id;         // 브랜드 ID
    private String name;     // 브랜드 이름


    public BrandDTO(Long id, String name) {
        this.id = id;
        this.name = name;

    }

    public BrandDTO(String name) {
        this.name = name;

    }


}

