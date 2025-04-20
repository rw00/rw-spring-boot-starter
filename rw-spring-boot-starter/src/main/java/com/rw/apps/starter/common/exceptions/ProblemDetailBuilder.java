package com.rw.apps.starter.common.exceptions;

import java.net.URI;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

public class ProblemDetailBuilder {
    private final ProblemDetail problemDetail;

    public ProblemDetailBuilder(HttpStatusCode status, String detail) {
        problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
    }

    public ProblemDetailBuilder type(ProblemTypes type) {
        problemDetail.setType(URI.create(type.getType()));
        return this;
    }

    public ProblemDetailBuilder errorCode(ErrorCodes errorCode) {
        problemDetail.setProperty("errorCode", errorCode);
        return this;
    }

    public ProblemDetail build() {
        return problemDetail;
    }
}
