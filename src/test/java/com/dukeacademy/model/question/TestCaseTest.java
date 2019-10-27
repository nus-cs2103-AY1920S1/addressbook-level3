package com.dukeacademy.model.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.entities.TestCase;


class TestCaseTest {
    @Test void testConstructorAndGetters() {
        String input = "1";
        String expectedResult = "2";
        TestCase testCase = new TestCase(input, expectedResult);
        assertEquals(input, testCase.getInput());
        assertEquals(expectedResult, testCase.getExpectedResult());
    }

    @Test void testConstructorNullArguments() {
        assertThrows(NullPointerException.class, () -> new TestCase(null, null));
        assertThrows(NullPointerException.class, () -> new TestCase("1", null));
        assertThrows(NullPointerException.class, () -> new TestCase(null, "1"));
    }
}
