package org.kgromov.apifirst.server.exceptions;

import org.zalando.problem.AbstractThrowableProblem;

import static org.zalando.problem.Status.NOT_FOUND;

public class ResourceNotFoundException extends AbstractThrowableProblem {

    public ResourceNotFoundException() {
        this("Requested Resource Not Found");
    }

    public ResourceNotFoundException(String message) {
        this(message, null);
    }

    public ResourceNotFoundException(String message, String detail) {
        super(null, message, NOT_FOUND, detail);
    }
}
