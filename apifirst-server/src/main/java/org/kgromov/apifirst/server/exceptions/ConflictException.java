package org.kgromov.apifirst.server.exceptions;

import org.zalando.problem.AbstractThrowableProblem;

import static org.zalando.problem.Status.CONFLICT;

public class ConflictException extends AbstractThrowableProblem {
    public ConflictException(String message) {
        super(null, message, CONFLICT);
    }
}
