package com.dukeacademy.model.question.entities;

import static com.dukeacademy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Represents a test case for a question.
 */
public class TestCase {
    private final String input;
    private final String expectedResult;

    /**
     * Instantiates a new Test case.
     *
     * @param input          the input
     * @param expectedResult the expected result
     */
    @JsonCreator
    public TestCase(String input, String expectedResult) {
        requireAllNonNull(input, expectedResult);
        this.input = input;
        this.expectedResult = expectedResult;
    }

    /**
     * Gets input.
     *
     * @return the input
     */
    @JsonValue
    public String getInput() {
        return input;
    }

    /**
     * Gets expected result.
     *
     * @return the expected result
     */
    @JsonValue
    public String getExpectedResult() {
        return expectedResult;
    }

    @Override
    public String toString() {
        return "Input: " + input + " Expected: " + expectedResult;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TestCase) {
            return ((TestCase) o).expectedResult.equals(this.expectedResult)
                    && ((TestCase) o).input.equals(this.input);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, expectedResult);
    }
}
