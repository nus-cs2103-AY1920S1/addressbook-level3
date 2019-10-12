package com.dukeacademy.model.solution;

import com.dukeacademy.solution.models.RuntimeError;

import java.util.Optional;

/**
 * Represents the results of running the user's solution against an individual test case. It contains a boolean
 * flag to indicate the success of running the test case, along with the expected and actual results.
 */
public class TestCaseResult {
    private final boolean isSuccessful;
    private final String expectedOutput;
    private final String actualOutput;
    private final Optional<RuntimeError> runtimeError;

    public TestCaseResult(boolean isSuccessful, String expectedOutput, String actualOutput) {
        this.isSuccessful = isSuccessful;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.runtimeError = Optional.empty();
    }

    private TestCaseResult(boolean isSuccessful, String expectedOutput,
                           String actualOutput, Optional<RuntimeError> runtimeError) {
        this.isSuccessful = isSuccessful;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.runtimeError = runtimeError;
    }

    public static TestCaseResult getErroredTestCaseResult(String expectedOutput, String errorMessage) {
        RuntimeError error = new RuntimeError(errorMessage);
        return new TestCaseResult(false, expectedOutput, "", Optional.of(error));
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

    public Optional<RuntimeError> getRuntimeError() {
        return this.runtimeError;
    }

    @Override
    public String toString() {
        return "Success: " + this.isSuccessful + "\n"
                + "Expected: " + this.expectedOutput
                + "Actual: " + this.actualOutput
                + "Error: " + this.runtimeError.isPresent() + "\n";
    }
}
