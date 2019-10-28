package com.dukeacademy.testexecutor.executor.exceptions;

/**
 * Exception thrown by the program executor.
 */
public class ProgramExecutorException extends Exception {
    /**
     * Instantiates a new Program executor exception.
     *
     * @param message the message
     */
    public ProgramExecutorException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Program executor exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ProgramExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
