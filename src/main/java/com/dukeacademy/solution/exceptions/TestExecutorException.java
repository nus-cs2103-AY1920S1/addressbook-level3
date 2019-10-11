package com.dukeacademy.solution.exceptions;

public class TestExecutorException extends Exception {
    public TestExecutorException(String message) {
        super(message);
    }

    public TestExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
