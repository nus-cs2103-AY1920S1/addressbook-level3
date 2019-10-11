package com.dukeacademy.solution.exceptions;

/**
 * Unchecked wrapper for the checked TestExecutorException to allow for calling of methods in lambdas. This
 * is to allow for use of the Java streams library to parallelize the execution of test cases.
 */
public class TestExecutorExceptionWrapper extends RuntimeException {
    public TestExecutorExceptionWrapper(String message) {
        super(message);
    }
}
