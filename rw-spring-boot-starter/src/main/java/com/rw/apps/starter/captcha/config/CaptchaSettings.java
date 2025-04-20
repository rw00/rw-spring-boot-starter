package com.rw.apps.starter.captcha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("captcha")
public record CaptchaSettings(Key key, double threshold) {

    @ConfigurationProperties("captcha.key")
    public record Key(String site, String secret) {
    }
}
