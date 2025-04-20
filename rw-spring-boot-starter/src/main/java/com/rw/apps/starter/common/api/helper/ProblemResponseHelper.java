package com.rw.apps.starter.common.api.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

@Component
public class ProblemResponseHelper {
    private final ObjectMapper objectMapper;

    public ProblemResponseHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void writeErrorResponse(HttpServletResponse response, ProblemDetail problemDetail) throws IOException {
        response.setStatus(problemDetail.getStatus());
        String responseBody = objectMapper.writeValueAsString(problemDetail);
        response.getWriter().write(responseBody);
    }
}
