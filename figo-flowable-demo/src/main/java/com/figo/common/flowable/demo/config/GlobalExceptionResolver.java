package com.figo.common.flowable.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.figo.common.flowable.model.result.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class GlobalExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final String MESSAGE_FORMAT = "%s: %s";

    private final ObjectMapper mapper;

    public GlobalExceptionResolver(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        GlobalExceptionResolver.log.error("Exception: {}", ex.getMessage(), ex);
        AjaxResult webreturn = this.exceptionReturn(ex);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            response.getOutputStream().write(this.mapper.writeValueAsString(webreturn).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }

    private AjaxResult exceptionReturn(Exception exception) {
        return AjaxResult.error(exception.getMessage());
    }
}
