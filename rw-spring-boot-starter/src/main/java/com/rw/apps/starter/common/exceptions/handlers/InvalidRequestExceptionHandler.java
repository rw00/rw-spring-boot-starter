package com.rw.apps.starter.common.exceptions.handlers;

import com.rw.apps.starter.common.exceptions.InvalidRequestException;
import com.rw.apps.starter.common.exceptions.ProblemDetailBuilder;
import com.rw.apps.starter.common.exceptions.ProblemTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class InvalidRequestExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ProblemDetail handleInvalidRequestException(InvalidRequestException ex) {
        return new ProblemDetailBuilder(HttpStatus.BAD_REQUEST, ex.getMessage())
                .type(ProblemTypes.INVALID_REQUEST)
                .build();
    }
}
