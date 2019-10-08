package com.dukeacademy.model;

/**
 * Represents a user's submission for a question.
 */
public class UserProgram {
    private String className;
    private String sourceCode;

    public String getClassName() {
        return this.className;
    }

    public String getSourceCodeAsString() {
        return this.sourceCode;
    }
}
