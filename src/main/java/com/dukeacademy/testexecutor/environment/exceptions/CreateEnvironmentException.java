package com.dukeacademy.testexecutor.environment.exceptions;

/**
 * Exception thrown by a CompilerEnvironment when it fails to be instantiated.
 */
public class CreateEnvironmentException extends Exception {
    public CreateEnvironmentException(String message) {
        super(message);
    }

    public CreateEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
