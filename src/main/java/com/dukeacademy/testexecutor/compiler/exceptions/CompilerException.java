package com.dukeacademy.testexecutor.compiler.exceptions;

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
