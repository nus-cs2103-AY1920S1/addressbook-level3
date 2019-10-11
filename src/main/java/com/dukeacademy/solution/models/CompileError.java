package com.dukeacademy.solution.models;

public class CompileError {
    private final String errorMessage;

    public CompileError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
