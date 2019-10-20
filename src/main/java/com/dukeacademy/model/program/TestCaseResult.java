package com.dukeacademy.model.program;

import java.util.Optional;

import com.dukeacademy.testexecutor.models.RuntimeError;

/**
 * Represents the results of running the user's solution against an individual test case. It contains a boolean
 * flag to indicate the success of running the test case, along with the expected and actual results.
 */
public class TestCaseResult {
    private final boolean isSuccessful;
    private final String input;
    private final String expectedOutput;
    private final String actualOutput;
    private final RuntimeError runtimeError;

    private TestCaseResult(boolean isSuccessful, String input, String expectedOutput,
                           String actualOutput, RuntimeError runtimeError) {
        this.isSuccessful = isSuccessful;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.runtimeError = runtimeError;
    }

    public static TestCaseResult getSuccessfulTestCaseResult(String input, String expectedOutput, String actualOutput) {
        return new TestCaseResult(true, input, expectedOutput, actualOutput, null);
    }

    public static TestCaseResult getFailedTestCaseResult(String input, String expectedOutput, String actualOutput) {
        return new TestCaseResult(false, input, expectedOutput, actualOutput, null);
    }

    public static TestCaseResult getErroredTestCaseResult(String input, String expectedOutput, String errorMessage) {
        RuntimeError error = new RuntimeError(errorMessage);
        return new TestCaseResult(false, input, expectedOutput, null, error);
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getInput() {
        return this.input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public Optional<String> getActualOutput() {
        return Optional.ofNullable(actualOutput);
    }

    public Optional<RuntimeError> getRuntimeError() {
        return Optional.ofNullable(this.runtimeError);
    }

    @Override
    public String toString() {
        return "Success: " + this.isSuccessful + "\n"
                + "Expected: " + this.expectedOutput
                + "Actual: " + this.actualOutput
                + "Error: " + this.getRuntimeError().isPresent() + "\n";
    }
}
