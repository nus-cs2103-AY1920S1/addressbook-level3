package com.dukeacademy.testexecutor.models;

/**
 * Represents a runtime error encountered when executing a Java program. The current implementation only contains the
 * error message.
 */
public class RuntimeError {
    private final String errorMessage;

    /**
     * Instantiates a new Runtime error.
     *
     * @param errorMessage the error message
     */
    public RuntimeError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof RuntimeError) {
            RuntimeError other = (RuntimeError) object;
            return other.errorMessage.equals(this.errorMessage);
        } else {
            return false;
        }
    }
}
