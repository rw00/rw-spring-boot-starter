package com.rw.apps.starter.common.logging.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rw.apps.starter.common.util.OperationUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ResponseLoggingService {
    private static final Logger log = LoggerFactory.getLogger(ResponseLoggingService.class);
    private static final String RESPONSE_PREFIX = "RESPONSE";
    private final ObjectMapper objectMapper;

    public ResponseLoggingService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void logResponse(HttpServletResponse response, Object body) {
        logSeparator();
        logStatus(response.getStatus());
        logHeaders(response);
        logBody(body);
        logEndOfRequest();
    }

    private void logSeparator() {
        log.info(">>>>>>>>>>>>>>>>>RESPONSE SENT>>>>>>>>>>>>>>>>");
    }

    private void logEndOfRequest() {
        log.info("----------------------------------------------");
        log.info("|-----------------END REQUEST----------------|");
        log.info("----------------------------------------------");
    }

    private void logStatus(int status) {
        log.info(RESPONSE_PREFIX + " STATUS: {}", status);
    }

    private void logBody(Object body) {
        OperationUtil.call(() -> log.info(RESPONSE_PREFIX + " BODY: {}", objectMapper.writeValueAsString(body)));
    }

    private void logHeaders(HttpServletResponse response) {
        Map<String, String> headers = getHeaders(response);
        if (!headers.isEmpty()) {
            log.info(RESPONSE_PREFIX + " HEADERS: {}", headers);
        }
    }

    private Map<String, String> getHeaders(HttpServletResponse response) {
        return response.getHeaderNames()
                       .stream()
                       .collect(Collectors.toMap(Function.identity(), response::getHeader, (k1, k2) -> k2));
    }
}
