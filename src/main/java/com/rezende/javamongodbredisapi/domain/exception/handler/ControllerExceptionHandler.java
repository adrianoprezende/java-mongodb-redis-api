package com.rezende.javamongodbredisapi.domain.exception.handler;

import com.rezende.javamongodbredisapi.domain.exception.BaseException;
import com.rezende.javamongodbredisapi.domain.exception.CategoryNotFoundException;
import com.rezende.javamongodbredisapi.domain.exception.ProductNotFoundException;
import com.rezende.javamongodbredisapi.domain.exception.ValidationException;
import com.rezende.javamongodbredisapi.endpoint.response.ErrorResponse;
import com.rezende.javamongodbredisapi.endpoint.response.Meta;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({CategoryNotFoundException.class, ProductNotFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(BaseException ex) {
        return handleBaseException(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        return handleBaseException(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<ErrorResponse> handleGenericException(RuntimeException ex) {
        return handleBaseException(new BaseException("Erro Interno", ex.getMessage(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedException(Exception ex) {
        return handleBaseException(new BaseException("Método Não Suportado", ex.getMessage(), String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()), ex), HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpStatus httpStatus) {
        ErrorResponse response = new ErrorResponse(ex.getErrors(), Meta.builder().totalPages(1).totalRecords(1L).build());
        ExceptionResponseContextThread.setExceptionResponse(response);

        return new ResponseEntity<>(response, new HttpHeaders(), httpStatus);
    }
}