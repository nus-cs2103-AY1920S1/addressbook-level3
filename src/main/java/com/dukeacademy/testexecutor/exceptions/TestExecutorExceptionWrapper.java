package com.dukeacademy.testexecutor.exceptions;

/**
 * Unchecked wrapper for the checked TestExecutorException to allow for calling of methods in lambdas. This
 * is to allow for use of the Java streams library to parallelize the execution of test cases.
 */
public class TestExecutorExceptionWrapper extends RuntimeException {
    /**
     * Instantiates a new Test executor exception wrapper.
     *
     * @param message the message
     */
    public TestExecutorExceptionWrapper(String message) {
        super(message);
    }
}
