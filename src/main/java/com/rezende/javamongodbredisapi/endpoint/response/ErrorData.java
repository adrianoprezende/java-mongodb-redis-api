package com.rezende.javamongodbredisapi.endpoint.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorData implements Serializable {
    @Serial
    private static final long serialVersionUID = -64626000272232381L;
    private String title;
    private String code;
    private String detail;
}
