package com.dukeacademy.solution.exceptions;

/**
 * Exception thrown by the compiler environment when an error occurs in creating a file.
 */
public class JavaFileCreationException extends Exception {
    public JavaFileCreationException(String message) {
        super(message);
    }

    public JavaFileCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
