package com.dukeacademy.solutions.exceptions;

/**
 * Execution thrown when the TestExecutor fails.
 */
public class TestExecutorException extends Exception {
    public TestExecutorException(String message) {
        super(message);
    }

    public TestExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
