package com.rw.apps.starter.common.security.helper;

import com.rw.apps.starter.common.api.cookie.CookieUtil;
import com.rw.apps.starter.common.security.jwt.JwtCookieHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;

public class LogoutHelper {
    private final JwtCookieHelper jwtCookieHelper;

    public LogoutHelper(JwtCookieHelper jwtCookieHelper) {
        this.jwtCookieHelper = jwtCookieHelper;
    }

    public void doLogout(HttpServletRequest request, HttpServletResponse response) {
        jwtCookieHelper.deleteJwtCookie(response);
        HttpSession session = request.getSession(false);
        if (session != null) { // there shouldn't be a session but just in case
            CookieUtil.deleteCookie(request, response, CookieUtil.SESSION_COOKIE_NAME);
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
    }
}
