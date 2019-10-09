package com.dukeacademy.model.solution;

/**
 * Represents a user's submission for a question.
 */
public class UserProgram {
    private String className;
    private String sourceCode;

    public UserProgram(String className, String sourceCode) {
        this.className = className;
        this.sourceCode = sourceCode;
    }

    public String getClassName() {
        return this.className;
    }

    public String getSourceCodeAsString() {
        return this.sourceCode;
    }
}
