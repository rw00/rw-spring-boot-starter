package com.rw.apps.starter.common.exceptions.handlers;

import com.rw.apps.starter.common.exceptions.ProblemDetailBuilder;
import com.rw.apps.starter.common.exceptions.ProblemTypes;
import com.rw.apps.starter.common.exceptions.auth.AuthException;
import com.rw.apps.starter.common.exceptions.auth.ForbiddenException;
import com.rw.apps.starter.common.exceptions.auth.UnauthorizedException;
import com.rw.apps.starter.common.exceptions.auth.UnauthorizedExpiredSessionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionsHandler {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail handleUnauthorizedException(UnauthorizedException e) {
        return new ProblemDetailBuilder(HttpStatus.UNAUTHORIZED, e.getMessage())
                .type(ProblemTypes.UNAUTHENTICATED)
                .build();
    }

    @ExceptionHandler(UnauthorizedExpiredSessionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail handleUnauthorizedExpiredSessionException(UnauthorizedExpiredSessionException e) {
        return new ProblemDetailBuilder(HttpStatus.UNAUTHORIZED, e.getMessage())
                .type(ProblemTypes.EXPIRED_SESSION)
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ProblemDetail handleForbiddenException(ForbiddenException e) {
        return new ProblemDetailBuilder(HttpStatus.FORBIDDEN, e.getMessage())
                .type(ProblemTypes.UNAUTHORIZED)
                .build();
    }

    public ProblemDetail handleAnyAuthException(AuthException e) {
        return switch (e) {
            case UnauthorizedExpiredSessionException expired -> handleUnauthorizedExpiredSessionException(expired);
            case UnauthorizedException unauthorized -> handleUnauthorizedException(unauthorized);
            case ForbiddenException forbidden -> handleForbiddenException(forbidden);
            default -> throw new RuntimeException("Unexpected auth error");
        };
    }
}
