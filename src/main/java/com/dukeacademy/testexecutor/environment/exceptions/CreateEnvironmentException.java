package com.dukeacademy.testexecutor.environment.exceptions;

public class CreateEnvironmentException extends Exception {
    public CreateEnvironmentException(String message) {
        super(message);
    }

    public CreateEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
