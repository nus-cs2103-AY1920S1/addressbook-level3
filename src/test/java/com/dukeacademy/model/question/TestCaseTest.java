package com.dukeacademy.model.question;

import com.dukeacademy.model.question.entities.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCaseTest {
    @Test
    public void constructor() {
        assertThrows(NullPointerException.class, () -> new TestCase(null, null));

        String input = "1";
        String expectedResult = "2";
        TestCase testCase = new TestCase(input, expectedResult);
        assertEquals(input, testCase.getInput());
        assertEquals(expectedResult, testCase.getExpectedResult());
    }
}