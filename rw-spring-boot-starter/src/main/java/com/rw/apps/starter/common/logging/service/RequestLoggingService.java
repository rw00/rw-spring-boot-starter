package com.rw.apps.starter.common.logging.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rw.apps.starter.common.util.OperationUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Component;

@Component
public class RequestLoggingService {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingService.class);
    private static final String REQUEST_PREFIX = "REQUEST ";
    private final ObjectMapper objectMapper;

    public RequestLoggingService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void logRequest(HttpServletRequest request, Object body) {
        logBeginOfRequest();
        logURI(request.getRequestURI());
        logSeparator();
        logMethod(request.getMethod());
        logHeaders(request);
        logParameters(request);
        logBody(body);
    }

    private void logBeginOfRequest() {
        log.info("______________________________________________");
        log.info("|----------------BEGIN REQUEST---------------|");
        log.info("----------------------------------------------");
    }

    private void logSeparator() {
        log.info("<<<<<<<<<<<<<<<REQUEST RECEIVED<<<<<<<<<<<<<<<");
    }

    private void logBody(Object body) {
        if (body != null) {
            OperationUtil.call(() -> log.info(REQUEST_PREFIX + " Body: {}", objectMapper.writeValueAsString(body)));
        }
    }

    private void logMethod(String method) {
        log.info(REQUEST_PREFIX + " Method: {}", method);
    }

    private void logURI(String path) {
        log.info(REQUEST_PREFIX + " URI: {}", path);
    }

    private void logParameters(HttpServletRequest request) {
        Map<String, String> parameters = getParameters(request);
        if (!parameters.isEmpty()) {
            log.info(REQUEST_PREFIX + " Parameters: {}", parameters);
        }
    }

    private Map<String, String> getParameters(HttpServletRequest request) {
        return StreamUtils.createStreamFromIterator(request.getParameterNames().asIterator())
                          .collect(Collectors.toMap(Function.identity(), request::getParameter, (k1, k2) -> k2));
    }

    private void logHeaders(HttpServletRequest request) {
        Map<String, String> headers = getHeaders(request);
        if (!headers.isEmpty()) {
            log.info(REQUEST_PREFIX + " Headers: {}", headers);
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        if (request.getHeaderNames() == null) {
            return Map.of();
        }
        return StreamUtils.createStreamFromIterator(request.getHeaderNames().asIterator())
                          .collect(Collectors.toMap(Function.identity(), request::getHeader, (k1, k2) -> k2));
    }
}
