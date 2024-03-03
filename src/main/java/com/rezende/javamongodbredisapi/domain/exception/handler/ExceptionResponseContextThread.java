package com.rezende.javamongodbredisapi.domain.exception.handler;

import com.rezende.javamongodbredisapi.endpoint.response.ErrorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionResponseContextThread {

    private static ThreadLocal<ErrorResponse> exceptionResponse = new ThreadLocal<>();

    public static void setExceptionResponse(ErrorResponse expResponse) {
        exceptionResponse.set(expResponse);
    }

    public static ErrorResponse getExceptionResponse() {
        return exceptionResponse.get();
    }

    public static void clear() {
        exceptionResponse.remove();
    }
}