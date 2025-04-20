package com.rw.apps.starter.common.env;

import com.rw.apps.starter.common.env.cfg.AppConfig;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvHelper {
    private final Environment environment;

    public EnvHelper(Environment environment) {
        this.environment = environment;
    }

    public boolean isLive() {
        return environment.matchesProfiles(AppConfig.LIVE_PROFILE);
    }

    public boolean isTest() {
        return environment.matchesProfiles(AppConfig.TEST_PROFILE);
    }

    public boolean isDev() {
        return environment.containsProperty("LOCAL_DEV");
    }
}
