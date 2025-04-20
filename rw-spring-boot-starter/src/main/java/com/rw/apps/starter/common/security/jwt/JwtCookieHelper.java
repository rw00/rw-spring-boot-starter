package com.rw.apps.starter.common.security.jwt;

import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public class JwtCookieHelper {
    public static final String TOKEN_COOKIE_NAME = "token";
    private final JwtConfigProperties jwtConfigProperties;

    public JwtCookieHelper(JwtConfigProperties jwtConfigProperties) {
        this.jwtConfigProperties = jwtConfigProperties;
    }

    public void addJwtCookie(HttpServletResponse response, String jwt) {
        ResponseCookie cookie = initJwtCookie(jwt);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void deleteJwtCookie(HttpServletResponse response) {
        ResponseCookie cookie = initJwtCookie(null);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private ResponseCookie initJwtCookie(String jwt) {
        return ResponseCookie.from(TOKEN_COOKIE_NAME, jwt != null ? jwt : "")
                             .path("/")
                             .httpOnly(true)
                             .secure(true)
                             .sameSite("Strict")
                             .maxAge(jwt != null ? jwtConfigProperties.expiration() : Duration.ZERO)
                             .build();
    }
}
