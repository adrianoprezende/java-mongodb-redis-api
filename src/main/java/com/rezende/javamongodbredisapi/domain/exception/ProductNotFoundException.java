package com.rezende.javamongodbredisapi.domain.exception;

import lombok.NoArgsConstructor;

import java.io.Serial;

public class ProductNotFoundException extends BaseException {
    @Serial
    private static final long serialVersionUID = 172441959797782838L;

    public ProductNotFoundException() {
        super("Produto não encontrado.");
    }

    public ProductNotFoundException(String title) {
        super(title);
    }

    public ProductNotFoundException(String title, String detail) {
        super(title, detail);
    }

    public ProductNotFoundException(String title, Throwable cause) {
        super(title, cause);
    }

    public ProductNotFoundException(String title, String detail, Throwable cause) {
        super(title, detail, cause);
    }
}
