package com.dukeacademy.testexecutor.models;

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof RuntimeError) {
            return ((RuntimeError) o).errorMessage.equals(this.errorMessage);
        } else {
            return false;
        }
    }
}
