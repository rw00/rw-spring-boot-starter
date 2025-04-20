package com.rw.apps.starter.captcha.service;

import com.rw.apps.starter.common.env.cfg.AppConfig;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(AppConfig.LOCAL_PROFILE)
public class LocalCacheCaptchaAttemptsService implements CaptchaAttemptsService {
    private final Map<String, Integer> failedAttempts = new ConcurrentHashMap<>();

    @Override
    public void captchaSucceeded(String key) {
        failedAttempts.remove(key);
    }

    @Override
    public void captchaFailed(String key) {
        failedAttempts.compute(key, (k, v) -> {
            if (v == null) {
                return 1;
            }
            return Math.min(v + 1, CaptchaAttemptsService.MAX_ATTEMPTS);
        });
    }

    @Override
    public boolean isBlocked(String key) {
        return failedAttempts.getOrDefault(key, 0) >= CaptchaAttemptsService.MAX_ATTEMPTS;
    }
}
