package com.rw.apps.starter.common.exceptions.auth;

public class UnauthorizedExpiredSessionException extends AuthException {
    public UnauthorizedExpiredSessionException() {
        super("Unauthorized");
    }
}
