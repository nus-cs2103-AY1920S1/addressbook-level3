package com.dukeacademy.testexecutor.exceptions;

/**
 * Exception thrown by compiler.
 */
public class CompilerException extends Exception {
    public CompilerException(String message) {
        super(message);
    }

    public CompilerException(String message, Throwable cause) {
        super(message, cause);
    }
}
