package com.rw.apps.starter.common.exceptions.auth;

public class ForbiddenException extends AuthException {
    public ForbiddenException(String message) {
        super(message);
    }
}
