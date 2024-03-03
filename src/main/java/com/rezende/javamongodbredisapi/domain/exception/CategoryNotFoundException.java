package com.rezende.javamongodbredisapi.domain.exception;

public class CategoryNotFoundException extends BaseException {

    public CategoryNotFoundException() {
        super("Categoria não encontrada.");
    }

    public CategoryNotFoundException(String title) {
        super(title);
    }

    public CategoryNotFoundException(String title, String detail) {
        super(title, detail);
    }

    public CategoryNotFoundException(String title, Throwable cause) {
        super(title, cause);
    }

    public CategoryNotFoundException(String title, String detail, Throwable cause) {
        super(title, detail, cause);
    }
}
