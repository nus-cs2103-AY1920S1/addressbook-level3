package com.dukeacademy.testexecutor.environment.exceptions;

/**
 * Exception thrown by the compiler environment when an error occurs in creating a file.
 */
public class JavaFileCreationException extends Exception {
    /**
     * Instantiates a new Java file creation exception.
     *
     * @param message the message
     */
    public JavaFileCreationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Java file creation exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public JavaFileCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
