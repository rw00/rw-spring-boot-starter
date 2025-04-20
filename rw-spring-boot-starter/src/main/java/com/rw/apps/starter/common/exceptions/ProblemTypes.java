package com.rw.apps.starter.common.exceptions;

public enum ProblemTypes {
    NOT_FOUND("not-found"),
    INVALID_REQUEST("invalid-request"),
    UNAUTHENTICATED("unauthenticated"),
    EXPIRED_SESSION("expired-session"),
    UNAUTHORIZED("unauthorized"),
    ;

    private final String type;

    ProblemTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
