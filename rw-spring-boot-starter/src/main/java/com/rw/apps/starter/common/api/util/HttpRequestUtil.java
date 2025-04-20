package com.rw.apps.starter.common.api.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class HttpRequestUtil {
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "WL-Proxy-Client-IP"
    };

    private HttpRequestUtil() {
    }

    public static String resolveClientIpAddress() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return "0.0.0.0";
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        for (String header : IP_HEADER_CANDIDATES) {
            String ipAddresses = request.getHeader(header);
            if (StringUtils.hasLength(ipAddresses) && !"unknown".equalsIgnoreCase(ipAddresses)) {
                return resolveLocalhostForIPv6(ipAddresses.split(",")[0]);
            }
        }
        String ipAddress = request.getRemoteAddr();
        return resolveLocalhostForIPv6(ipAddress);
    }

    private static String resolveLocalhostForIPv6(String ipAddress) {
        if ("0:0:0:0:0:0:0:1".equals(ipAddress)) {
            return "127.0.0.1";
        }
        return ipAddress;
    }
}
