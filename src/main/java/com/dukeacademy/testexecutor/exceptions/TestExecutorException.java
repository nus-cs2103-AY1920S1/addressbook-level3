package com.dukeacademy.testexecutor.exceptions;

/**
 * Execution thrown when the TestExecutor fails.
 */
public class TestExecutorException extends Exception {
    /**
     * Instantiates a new Test executor exception.
     *
     * @param message the message
     */
    public TestExecutorException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Test executor exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TestExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
