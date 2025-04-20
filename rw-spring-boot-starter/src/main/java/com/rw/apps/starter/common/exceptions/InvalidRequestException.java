package com.rw.apps.starter.common.exceptions;

public class InvalidRequestException extends RuntimeException {
    private final ErrorCodes errorCode;

    public InvalidRequestException(String msg) {
        this(msg, null);
    }

    public InvalidRequestException(String msg, ErrorCodes errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }
}
