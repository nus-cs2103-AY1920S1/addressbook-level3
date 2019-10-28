package com.dukeacademy.testexecutor.compiler.exceptions;

/**
 * Exception thrown by compiler.
 */
public class CompilerException extends Exception {
    /**
     * Instantiates a new Compiler exception.
     *
     * @param message the message
     */
    public CompilerException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Compiler exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public CompilerException(String message, Throwable cause) {
        super(message, cause);
    }
}
