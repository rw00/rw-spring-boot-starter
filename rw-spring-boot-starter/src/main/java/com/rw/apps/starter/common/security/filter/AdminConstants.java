package com.rw.apps.starter.common.security.filter;

import java.util.Set;

public final class AdminConstants {
    public static final Set<String> ADMIN_URLS = Set.of(
            "/admin",
            "/management",
            "/actuator"
    );

    private AdminConstants() {
    }
}
