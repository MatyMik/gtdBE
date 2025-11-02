package com.codebeforedawn.be.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "Resource not found";
    private static final String DEFAULT_ERROR_CODE = "RESOURCE_NOT_FOUND";
    private String errorCode;

    public NotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public NotFoundException(String message) {
        this(message, DEFAULT_ERROR_CODE);
    }

    public NotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
