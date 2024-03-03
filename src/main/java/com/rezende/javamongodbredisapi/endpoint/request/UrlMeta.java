package com.rezende.javamongodbredisapi.endpoint.request;

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
@AllArgsConstructor
@NoArgsConstructor
public class UrlMeta implements Serializable {

    @Serial
    private static final long serialVersionUID = 9133337247869051935L;

    @Builder.Default
    private String scheme = "";
    @Builder.Default
    private String requestHost = "";
    @Builder.Default
    private String url = "";
    @Builder.Default
    private String params = "";
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int pageSize = 1;
}
