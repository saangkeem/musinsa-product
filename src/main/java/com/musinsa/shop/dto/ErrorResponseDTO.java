package com.musinsa.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO {
    private String error;
    private String reason;

    // 기본 생성자
    public ErrorResponseDTO(String error, String reason) {
        this.error = error;
        this.reason = reason;
    }

}