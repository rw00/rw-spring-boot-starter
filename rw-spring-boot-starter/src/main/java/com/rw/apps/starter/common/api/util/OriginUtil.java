package com.rw.apps.starter.common.api.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.http.HttpHeaders;

public final class OriginUtil {
    private OriginUtil() {
    }

    public static String getOrigin(HttpServletRequest request) {
        return wrapHeader(request.getHeader(HttpHeaders.ORIGIN))
                .or(() -> wrapHeader(request.getHeader(HttpHeaders.REFERER)))
                .or(() -> wrapHeader(request.getHeader(HttpHeaders.HOST)))
                .orElse(null);
    }

    private static Optional<String> wrapHeader(String header) {
        return Optional.ofNullable(header);
    }
}
