package com.rezende.javamongodbredisapi.config;

import com.rezende.javamongodbredisapi.endpoint.interceptor.RestInterceptor;
import com.rezende.javamongodbredisapi.endpoint.request.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    RestInterceptor restInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(restInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**", "/error");
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
    }

    @Bean
    @RequestScope
    public RequestInfo requestScopeInformation() {
        return new RequestInfo();
    }

    @Bean
    public LocaleResolver localeResolver() {
        final Locale ptBrLocale = new Locale("pt", "BR");
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(ptBrLocale);
        return slr;
    }

}