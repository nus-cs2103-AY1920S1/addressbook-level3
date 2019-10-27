package com.dukeacademy.testexecutor.environment.exceptions;

/**
 * Exception thrown when the CompilerEnvironment fails to delete its containing files and folders.
 */
public class ClearEnvironmentException extends Exception {
    /**
     * Instantiates a new Clear environment exception.
     *
     * @param message the message
     */
    public ClearEnvironmentException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Clear environment exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ClearEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
