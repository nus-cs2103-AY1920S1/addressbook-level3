package com.dukeacademy.solution.exceptions;

/**
 * Exception thrown by the compiler environment.
 */
public class CompilerEnvironmentException extends Exception {
    public CompilerEnvironmentException(String message) {
        super(message);
    }

    public CompilerEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
