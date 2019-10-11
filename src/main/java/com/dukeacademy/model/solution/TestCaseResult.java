package com.dukeacademy.model.solution;

/**
 * Represents the results of running the user's solution against an individual test case. It contains a boolean
 * flag to indicate the success of running the test case, along with the expected and actual results.
 */
public class TestCaseResult {
    private boolean isSuccessful;
    private String expectedOutput;
    private String actualOutput;

    public TestCaseResult(boolean isSuccessful, String expectedOutput, String actualOutput) {
        this.isSuccessful = isSuccessful;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
    }

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
