package com.rw.apps.starter.common.api.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.util.StringUtils;

public final class CookieUtil {
    public static final String SESSION_COOKIE_NAME = "JSESSIONID";

    private CookieUtil() {
    }

    public static Optional<Cookie> findCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        return Optional.ofNullable(cookies).stream().flatMap(Arrays::stream)
                       .filter(cookie -> name.equals(cookie.getName()))
                       .findFirst();
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        ResponseCookie cookie = ResponseCookie.from(name, "")
                                              .maxAge(Duration.ZERO)
                                              .path(determineCookiePath(request))
                                              .secure(request.isSecure())
                                              .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private static String determineCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return StringUtils.hasText(contextPath) ? contextPath : "/";
    }
}
