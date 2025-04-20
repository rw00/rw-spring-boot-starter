package com.rw.apps.starter.common.security.jwt;

import com.rw.apps.starter.common.api.cookie.CookieUtil;
import com.rw.apps.starter.common.security.model.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;

public class JwtCookieExtractor implements JwtCurrentUserExtractor {
    private final JwtService jwtService;

    public JwtCookieExtractor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public CurrentUser extractUser(HttpServletRequest request) {
        return CookieUtil.findCookie(request, JwtCookieHelper.TOKEN_COOKIE_NAME)
                         .map(cookie -> jwtService.parseJwt(cookie.getValue()))
                         .orElse(null);
    }
}
