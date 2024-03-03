package com.rezende.javamongodbredisapi.endpoint.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 3767211894293945051L;

    private List<T> data;
    private ResponseLinks links;
    private Meta meta;
}
