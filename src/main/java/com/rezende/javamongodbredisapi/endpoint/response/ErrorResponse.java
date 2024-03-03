package com.rezende.javamongodbredisapi.endpoint.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 8551990712071558423L;

    private Set<ErrorData> errors = new HashSet<>();
    private Meta meta;
}
