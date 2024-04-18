package org.kgromov.apifirst.server.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    void conflict() {
        //
    }
}
