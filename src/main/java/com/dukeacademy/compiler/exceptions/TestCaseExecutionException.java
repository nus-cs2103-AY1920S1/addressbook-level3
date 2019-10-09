package com.dukeacademy.compiler.exceptions;

public class TestCaseExecutionException extends Exception {
    public TestCaseExecutionException(String message) {
        super(message);
    }

    public TestCaseExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
