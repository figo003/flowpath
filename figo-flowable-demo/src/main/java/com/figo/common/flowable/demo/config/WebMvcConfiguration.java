package com.figo.common.flowable.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration()
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;

    public WebMvcConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new GlobalExceptionResolver(this.objectMapper));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Parameter2DateConverter());
    }
}
