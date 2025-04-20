package com.rw.apps.starter.common.exceptions.handlers;

import com.rw.apps.starter.common.exceptions.NotFoundException;
import com.rw.apps.starter.common.exceptions.ProblemDetailBuilder;
import com.rw.apps.starter.common.exceptions.ProblemTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class NotFoundExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ProblemDetail handleNotFoundException(NotFoundException ex) {
        return new ProblemDetailBuilder(HttpStatus.NOT_FOUND, ex.getMessage())
                .type(ProblemTypes.NOT_FOUND)
                .build();
    }
}
