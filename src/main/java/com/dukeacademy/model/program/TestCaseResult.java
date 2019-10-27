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

    /**
     * Gets successful test case result.
     *
     * @param input        the input
     * @param actualOutput the actual output
     * @return the successful test case result
     */
    public static TestCaseResult getSuccessfulTestCaseResult(String input, String actualOutput) {
        return new TestCaseResult(true, input, actualOutput, actualOutput, null);
    }

    /**
     * Gets failed test case result.
     *
     * @param input          the input
     * @param expectedOutput the expected output
     * @param actualOutput   the actual output
     * @return the failed test case result
     */
    public static TestCaseResult getFailedTestCaseResult(String input, String expectedOutput, String actualOutput) {
        return new TestCaseResult(false, input, expectedOutput, actualOutput, null);
    }

    /**
     * Gets errored test case result.
     *
     * @param input          the input
     * @param expectedOutput the expected output
     * @param errorMessage   the error message
     * @return the errored test case result
     */
    public static TestCaseResult getErroredTestCaseResult(String input, String expectedOutput, String errorMessage) {
        RuntimeError error = new RuntimeError(errorMessage);
        return new TestCaseResult(false, input, expectedOutput, null, error);
    }

    /**
     * Is successful boolean.
     *
     * @return the boolean
     */
    public boolean isSuccessful() {
        return isSuccessful;
    }

    /**
     * Gets input.
     *
     * @return the input
     */
    public String getInput() {
        return this.input;
    }

    /**
     * Gets expected output.
     *
     * @return the expected output
     */
    public String getExpectedOutput() {
        return expectedOutput;
    }

    /**
     * Gets actual output.
     *
     * @return the actual output
     */
    public Optional<String> getActualOutput() {
        return Optional.ofNullable(actualOutput);
    }

    /**
     * Gets runtime error.
     *
     * @return the runtime error
     */
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

    @Override
    public boolean equals(Object object) {
        if (object instanceof TestCaseResult) {
            TestCaseResult other = (TestCaseResult) object;
            return other.isSuccessful == this.isSuccessful
                    && other.input.equals(this.input)
                    && other.expectedOutput.equals(this.expectedOutput)
                    && other.getActualOutput().equals(this.getActualOutput())
                    && other.getRuntimeError().equals(this.getRuntimeError());
        } else {
            return false;
        }
    }
}
