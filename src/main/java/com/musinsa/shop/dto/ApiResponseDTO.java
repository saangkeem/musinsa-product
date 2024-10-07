package com.musinsa.shop.dto;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO<T> {
    private String message;
    private T data;

    public ApiResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponseDTO(String message) {
        this.message = message;
    }
}
