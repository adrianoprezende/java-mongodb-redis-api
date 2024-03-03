package com.rezende.javamongodbredisapi.domain.exception;

import com.rezende.javamongodbredisapi.endpoint.response.ErrorData;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Set;

@Getter
@Setter
public class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 7337862194399781196L;
    private String detail;
    protected Set<ErrorData> errors;

    public BaseException(String title) {
        super(title);
        var errorData = ErrorData.builder().title(title).build();
        this.errors = Set.of(errorData);
    }

    public BaseException(String title, String detail) {
        super(title);
        this.detail = detail;
        var errorData = ErrorData.builder().title(title).detail(detail).build();
        this.errors = Set.of(errorData);
    }

    public BaseException(String title, Throwable cause) {
        super(title, cause);
        var errorData = ErrorData.builder().title(title).build();
        this.errors = Set.of(errorData);
    }

    public BaseException(String title, String detail, Throwable cause) {
        super(title, cause);
        this.detail = detail;
        var errorData = ErrorData.builder().title(title).detail(detail).build();
        this.errors = Set.of(errorData);
    }

    public BaseException(String title, String detail, String code, Throwable cause) {
        super(title, cause);
        this.detail = detail;
        var errorData = ErrorData.builder().title(title).detail(detail).code(code).build();
        this.errors = Set.of(errorData);
    }
}
