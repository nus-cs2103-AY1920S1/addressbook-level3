package com.dukeacademy.testexecutor.executor.exceptions;

/**
 * Exception thrown by the program executor.
 */
public class ProgramExecutorException extends Exception {
    public ProgramExecutorException(String message) {
        super(message);
    }

    public ProgramExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
