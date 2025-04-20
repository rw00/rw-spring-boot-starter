package com.rw.apps.starter.captcha.service;

public interface CaptchaService {
    void validateCaptcha(String captchaResponse, Actions action);

    enum Actions {
        REGISTER, LOGIN, CONTACT_US
    }
}
