package com.rw.apps.starter.common.exceptions.auth;

public class UnauthorizedException extends AuthException {
    public UnauthorizedException(String msg) {
        super(msg);
    }
}
