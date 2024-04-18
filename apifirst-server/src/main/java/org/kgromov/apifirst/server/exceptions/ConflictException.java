package org.kgromov.apifirst.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(Throwable e) {

    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
