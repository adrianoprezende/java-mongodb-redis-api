package com.rezende.javamongodbredisapi.domain.exception;

import com.rezende.javamongodbredisapi.endpoint.response.ErrorData;
import org.springframework.util.Assert;

import java.util.Set;

public class ValidationException extends BaseException {
    public ValidationException(Set<ErrorData> errors) {
        this((String)null, (Throwable)null, errors);
    }

    public ValidationException(String title, Throwable cause, Set<ErrorData> errors) {
        super(title, cause);
        Assert.notNull(errors, "Errors List must not be null");
        errors.forEach((error) -> {
            error.setTitle(error.getTitle());
        });
        this.errors = errors;
    }
}
