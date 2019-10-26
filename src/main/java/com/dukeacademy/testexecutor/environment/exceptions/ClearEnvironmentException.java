package com.dukeacademy.testexecutor.environment.exceptions;

/**
 * Exception thrown when the CompilerEnvironment fails to delete its containing files and folders.
 */
public class ClearEnvironmentException extends Exception {
    public ClearEnvironmentException(String message) {
        super(message);
    }

    public ClearEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
