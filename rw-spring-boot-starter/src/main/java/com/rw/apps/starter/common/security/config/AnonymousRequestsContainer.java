package com.rw.apps.starter.common.security.config;

import java.util.List;

public interface AnonymousRequestsContainer {
    List<String> getUrlPatterns();
}
