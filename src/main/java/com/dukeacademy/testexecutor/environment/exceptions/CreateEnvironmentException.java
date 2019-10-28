package com.dukeacademy.testexecutor.environment.exceptions;

/**
 * Exception thrown by a CompilerEnvironment when it fails to be instantiated.
 */
public class CreateEnvironmentException extends Exception {
    /**
     * Instantiates a new Create environment exception.
     *
     * @param message the message
     */
    public CreateEnvironmentException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Create environment exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public CreateEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
