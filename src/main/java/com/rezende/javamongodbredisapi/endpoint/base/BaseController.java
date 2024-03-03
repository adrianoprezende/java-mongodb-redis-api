package com.rezende.javamongodbredisapi.endpoint.base;

import com.rezende.javamongodbredisapi.endpoint.request.UrlMeta;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

public abstract class BaseController {

    @Value("${gateway.host:#{null}}")
    private String gatewayHost;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    protected UrlMeta getUrlMeta(HttpServletRequest request, int page, int size) {
        return UrlMeta.builder()
                .url(StringUtils.isBlank(request.getServletPath()) ? contextPath + request.getRequestURI() : request.getServletPath())
                .scheme(request.getScheme())
                .requestHost(Objects.nonNull(gatewayHost) ? gatewayHost : request.getRemoteHost())
                .page(page)
                .pageSize(size)
                .params(request.getQueryString()).build();
    }
}
