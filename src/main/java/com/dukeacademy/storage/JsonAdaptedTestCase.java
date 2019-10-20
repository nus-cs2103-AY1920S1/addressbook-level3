package com.dukeacademy.storage;

import com.dukeacademy.model.question.TestCase;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class JsonAdaptedTestCase {
    private final String input;
    private final String expectedResult;

    @JsonCreator
    public JsonAdaptedTestCase(String input, String expectedResult) {
        this.input = input;
        this.expectedResult = expectedResult;
    }

    public JsonAdaptedTestCase(TestCase source) {
        this.input = source.getInput();
        this.expectedResult = source.getExpectedResult();
    }

    @JsonProperty
    public String getInput() {
        return this.input;
    }

    @JsonProperty
    public String getExpectedResult() {
        return this.expectedResult;
    }

    public TestCase toModel() {
        return new TestCase(input, expectedResult);
    }
}
