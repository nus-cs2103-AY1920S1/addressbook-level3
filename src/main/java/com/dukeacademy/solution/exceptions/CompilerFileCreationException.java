package com.dukeacademy.solution.exceptions;

public class CompilerFileCreationException extends Exception {
    public CompilerFileCreationException(String message) {
        super(message);
    }

    public CompilerFileCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
