package org.kgromov.apifirst.server.exceptions;

import org.zalando.problem.AbstractThrowableProblem;

public class ResourceNotFoundException extends AbstractThrowableProblem {

    public ResourceNotFoundException() {
        this("Requested Entity Not Found");
    }

    public ResourceNotFoundException(String message) {
        this(message, null);
    }

    public ResourceNotFoundException(String message, String detail) {
        super(null, message, org.zalando.problem.Status.NOT_FOUND, detail);
    }
}
