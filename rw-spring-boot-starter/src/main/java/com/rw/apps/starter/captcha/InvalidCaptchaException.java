package com.rw.apps.starter.captcha;

public class InvalidCaptchaException extends RuntimeException {
    public InvalidCaptchaException(String msg) {
        super(msg);
    }
}
