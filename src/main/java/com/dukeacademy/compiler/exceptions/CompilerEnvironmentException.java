package com.dukeacademy.compiler.exceptions;

public class CompilerEnvironmentException extends Exception {
    public CompilerEnvironmentException(String message) {
        super(message);
    }

    public CompilerEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
