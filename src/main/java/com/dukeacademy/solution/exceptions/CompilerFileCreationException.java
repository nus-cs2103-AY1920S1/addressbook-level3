package com.dukeacademy.solution.exceptions;

/**
 * Exception thrown by the compiler environment when an error occurs in creating a file.
 */
public class CompilerFileCreationException extends Exception {
    public CompilerFileCreationException(String message) {
        super(message);
    }

    public CompilerFileCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
