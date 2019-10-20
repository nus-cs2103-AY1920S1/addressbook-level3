package com.dukeacademy.storage.question;

import com.dukeacademy.model.question.entities.TestCase;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonAdaptedTestCase {
    private final String input;
    private final String expectedResult;

    @JsonCreator
    public JsonAdaptedTestCase(@JsonProperty("input") String input, @JsonProperty("expectedResult") String expectedResult) {
        this.input = input;
        this.expectedResult = expectedResult;
    }

    public JsonAdaptedTestCase(TestCase source) {
        this.input = source.getInput();
        this.expectedResult = source.getExpectedResult();
    }

    public TestCase toModel() {
        return new TestCase(input, expectedResult);
    }
}
