package com.rw.apps.starter.common.security.jwt;

import com.rw.apps.starter.common.security.model.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtCurrentUserExtractor {
    CurrentUser extractUser(HttpServletRequest request);
}
