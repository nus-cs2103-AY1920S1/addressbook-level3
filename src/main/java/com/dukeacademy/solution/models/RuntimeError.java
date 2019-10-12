package com.dukeacademy.solution.models;

/**
 * Represents a runtime error encountered when executing a test case against a program.
 */
public class RuntimeError {
    private final String errorMessage;

    public RuntimeError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
