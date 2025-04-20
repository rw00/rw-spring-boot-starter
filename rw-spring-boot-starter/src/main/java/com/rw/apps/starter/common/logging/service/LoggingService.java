package com.rw.apps.starter.common.logging.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class LoggingService {
    private final RequestLoggingService requestLoggingService;
    private final ResponseLoggingService responseLoggingService;

    public LoggingService(RequestLoggingService requestLoggingService, ResponseLoggingService responseLoggingService) {
        this.requestLoggingService = requestLoggingService;
        this.responseLoggingService = responseLoggingService;
    }

    public void logRequest(HttpServletRequest request, Object body) {
        requestLoggingService.logRequest(request, body);
    }

    public void logResponse(HttpServletResponse response, Object body) {
        responseLoggingService.logResponse(response, body);
    }
}
