package org.kgromov.apifirst.server.controllers;

import org.kgromov.apifirst.server.controllers.traits.DataIntegrityViolationTrait;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice implements DataIntegrityViolationTrait {
}
