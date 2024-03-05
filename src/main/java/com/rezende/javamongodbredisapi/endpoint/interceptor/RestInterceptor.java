package com.rezende.javamongodbredisapi.endpoint.interceptor;

import com.rezende.javamongodbredisapi.domain.exception.handler.ExceptionResponseContextThread;
import com.rezende.javamongodbredisapi.endpoint.request.RequestInfo;
import com.rezende.javamongodbredisapi.endpoint.response.ErrorData;
import com.rezende.javamongodbredisapi.endpoint.response.ErrorResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RestInterceptor implements HandlerInterceptor {

    private static final int STATUS_ERROR = 300;

    @Value("${application-version}")
    private String applicationVersion;

    @Value("${filter.ignoredUrisRegex}")
    private List<String> ignoredUrisRegex;

    @Resource(name = "requestScopeInformation")
    private final RequestInfo requestInfo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        requestHandle(request, response);

        final String logFormat = "[Logging Request: endpoint={} {} {} {}, requestId={}, serviceVersion={}]";

        log.info(logFormat, request.getMethod(), request.getRemoteAddr(), request.getRequestURI(), request.getQueryString(), requestInfo.getRequestId(),
                applicationVersion);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handler, Exception ex){
        final String logFormat = "[Logging Response: endpoint={} {} {} {}, requestId={}, httpStatus={}, serviceVersion={}] [exceptionMessage={}, errorCode={}]";

        String errorMessage = "";
        Integer errorCode = null;

        if (response.getStatus() > STATUS_ERROR) {

            final ErrorResponse errorDataMessage = ExceptionResponseContextThread.getExceptionResponse();

            errorMessage = errorDataMessage != null ? errorDataMessage.getErrors().stream().map(ErrorData::getDetail).collect(Collectors.joining(",")) : null;
            errorCode = response.getStatus();
        }

        log.info(logFormat, request.getMethod(), request.getRemoteAddr(), request.getRequestURI(), request.getQueryString(), requestInfo.getRequestId(),
                response.getStatus(), applicationVersion, errorMessage, errorCode);

        ExceptionResponseContextThread.clear();
    }

    private void requestHandle(HttpServletRequest request, HttpServletResponse response) {
        final String requestURI = request.getRequestURI();
        final String requestId = getRequestId(request);

        response.setHeader("request-id", validateHeader(requestId));
        response.setHeader("building-version", applicationVersion);
        if (requestURI != null) {
            final boolean ignoredUriFound = ignoredUrisRegex.stream().anyMatch(requestURI::matches);
            if (ignoredUriFound) {
                return;
            }
        }
        requestInfo.setRequestId(requestId);
    }

    private String validateHeader(String xfapi) {
        try {
            if (!StringUtils.isEmpty(xfapi)) {
                return validateUUID(xfapi);
            }
            return null;
        } catch (IllegalArgumentException e) {
            log.error("[RestInterceptor] Header request-id fora do Padrão (UUID-4).");
            throw new IllegalArgumentException("request-id fora do Padrão (UUID-4)");
        }
    }

    private String validateUUID(String xfapi){
        UUID uuid = UUID.fromString(xfapi);
        log.debug("[RestInterceptor] Header request-id válido");
        return uuid.toString();
    }

    private String getRequestId(final HttpServletRequest request) {
        String requestId = request.getHeader("request-id");
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }
        return requestId;
    }
}
