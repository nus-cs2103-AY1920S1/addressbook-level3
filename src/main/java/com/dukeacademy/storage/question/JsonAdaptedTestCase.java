package com.dukeacademy.storage.question;

import com.dukeacademy.model.question.entities.TestCase;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json friendly representation of the test case model for read and write by the Jackson library.
 */
public class JsonAdaptedTestCase {
    private final String input;
    private final String expectedResult;

    /**
     * Instantiates a new Json adapted test case.
     *
     * @param input          the input
     * @param expectedResult the expected result
     */
    @JsonCreator
    public JsonAdaptedTestCase(@JsonProperty("input") String input,
                               @JsonProperty("expectedResult") String expectedResult) {
        this.input = input;
        this.expectedResult = expectedResult;
    }

    /**
     * Instantiates a new Json adapted test case.
     *
     * @param source the source
     */
    public JsonAdaptedTestCase(TestCase source) {
        this.input = source.getInput();
        this.expectedResult = source.getExpectedResult();
    }

    /**
     * To model test case.
     *
     * @return the test case
     */
    public TestCase toModel() {
        return new TestCase(input, expectedResult);
    }
}
