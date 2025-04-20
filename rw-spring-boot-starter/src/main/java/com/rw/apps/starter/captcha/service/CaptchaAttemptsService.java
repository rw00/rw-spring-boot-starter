package com.rw.apps.starter.captcha.service;

public interface CaptchaAttemptsService {
    int MAX_ATTEMPTS = 4;

    void captchaSucceeded(String key);

    void captchaFailed(String key);

    boolean isBlocked(String key);
}
