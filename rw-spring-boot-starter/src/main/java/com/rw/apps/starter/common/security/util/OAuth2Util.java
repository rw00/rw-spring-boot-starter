package com.rw.apps.starter.common.security.util;

import org.springframework.security.oauth2.core.user.OAuth2User;

public final class OAuth2Util {
    private OAuth2Util() {
    }

    public static String getEmail(OAuth2User user) {
        return user.getAttribute("email");
    }
}
