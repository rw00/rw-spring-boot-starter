package com.rw.apps.starter.captcha.service;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.rw.apps.starter.cache.BaseCacheBuilder;
import com.rw.apps.starter.common.env.cfg.AppConfig;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(AppConfig.LIVE_PROFILE)
public class DefaultCaptchaAttemptsService implements CaptchaAttemptsService {
    private final LoadingCache<String, Integer> attemptsCache;

    public DefaultCaptchaAttemptsService() {
        attemptsCache = BaseCacheBuilder.defaultBuilder()
                                        .maximumSize(2000)
                                        .expireAfterWrite(4, TimeUnit.HOURS)
                                        .build(key -> 0);
    }

    @Override
    public void captchaSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    @Override
    public void captchaFailed(String key) {
        Integer attempts = attemptsCache.get(key);
        attemptsCache.put(key, attempts + 1);
    }

    @Override
    public boolean isBlocked(String key) {
        return attemptsCache.get(key) >= CaptchaAttemptsService.MAX_ATTEMPTS;
    }
}
