package com.dukeacademy.model.question.entities;

import static com.dukeacademy.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a test case for a question.
 */
public class TestCase {
    private final String input;
    private final String expectedResult;

    public TestCase(String input, String expectedResult) {
        requireAllNonNull(input, expectedResult);
        this.input = input;
        this.expectedResult = expectedResult;
    }

    public String getInput() {
        return input;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    @Override
    public String toString() {
        return "Input: " + input + "Expected: " + expectedResult;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TestCase) {
            return ((TestCase) o).expectedResult.equals(this.expectedResult)
                    && ((TestCase) o).input.equals(this.input);
        }

        return false;
    }
}
