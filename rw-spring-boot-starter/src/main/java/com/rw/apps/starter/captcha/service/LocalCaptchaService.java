package com.rw.apps.starter.captcha.service;

import com.rw.apps.starter.common.env.cfg.AppConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(AppConfig.LOCAL_PROFILE)
public class LocalCaptchaService implements CaptchaService {
    @Override
    public void validateCaptcha(String captchaResponse, Actions action) {
    }
}
