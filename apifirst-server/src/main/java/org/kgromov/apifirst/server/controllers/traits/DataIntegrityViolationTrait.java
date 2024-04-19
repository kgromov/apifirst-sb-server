package org.kgromov.apifirst.server.controllers.traits;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.AdviceTrait;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import static org.zalando.problem.Status.CONFLICT;

// seems ProblemHandling is auto configured with starter - try it put
public interface DataIntegrityViolationTrait extends AdviceTrait, ProblemHandling {
    @ExceptionHandler
    default ResponseEntity<Problem> handleDataDataIntegrityViolation(DataIntegrityViolationException e,
                                                                     NativeWebRequest request) {
        return create(CONFLICT, e, request);
    }
}
