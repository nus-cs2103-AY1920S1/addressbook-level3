package com.dukeacademy.model.compiler;

/**
 * Represents the results of running the user's solution against an individual test case.
 */
public class TestCaseResult {
    private boolean isSuccessful;
    private String expectedOutput;
    private String actualOutput;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public String getActualOutput() {
        return actualOutput;
    }
}
