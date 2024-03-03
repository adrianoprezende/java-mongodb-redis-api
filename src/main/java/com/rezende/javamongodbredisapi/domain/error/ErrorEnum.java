package com.rezende.javamongodbredisapi.domain.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum ErrorEnum {
    VALIDATION_ERROR("Erro de validação", "01");

    private final String description;
    private final String code;
}
