package com.rw.apps.starter.common.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }

    public static NotFoundException account() {
        return new NotFoundException("Account not found");
    }
}
