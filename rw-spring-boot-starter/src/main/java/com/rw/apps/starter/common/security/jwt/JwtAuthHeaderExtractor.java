package com.rw.apps.starter.common.security.jwt;

import com.rw.apps.starter.common.security.model.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public class JwtAuthHeaderExtractor implements JwtCurrentUserExtractor {
    private static final String AUTH_BEARER = "Bearer ";
    private final JwtService jwtService;

    public JwtAuthHeaderExtractor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public CurrentUser extractUser(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(AUTH_BEARER)) {
            String jwt = authHeader.substring(AUTH_BEARER.length());
            return jwtService.parseJwt(jwt);
        }
        return null;
    }
}
