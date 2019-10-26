package com.dukeacademy.model.program;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestCaseResultTest {
    @Test
    void testStaticConstructorAndGetter_SuccessfulResult() {
        TestCaseResult testCase = TestCaseResult.getSuccessfulTestCaseResult("1",  "2");
        assertTrue(testCase.isSuccessful());
        assertFalse(testCase.getRuntimeError().isPresent());
        assertEquals("1", testCase.getInput());
        assertEquals("2", testCase.getExpectedOutput());
        assertTrue(testCase.getActualOutput().isPresent());
        assertEquals("2", testCase.getActualOutput().get());
    }

    @Test
    void testStaticConstructorAndGetter_FailedResult() {
        TestCaseResult testCase = TestCaseResult.getFailedTestCaseResult("1",  "2", "3");
        assertFalse(testCase.isSuccessful());
        assertFalse(testCase.getRuntimeError().isPresent());
        assertEquals("1", testCase.getInput());
        assertEquals("2", testCase.getExpectedOutput());
        assertTrue(testCase.getActualOutput().isPresent());
        assertEquals("3", testCase.getActualOutput().get());
    }

    @Test
    void testStaticConstructorAndGetter_ErroredResult() {
        String error = "This is an error!";
        TestCaseResult testCase = TestCaseResult.getErroredTestCaseResult("1",  "2", error);
        assertFalse(testCase.isSuccessful());
        assertTrue(testCase.getRuntimeError().isPresent());
        assertEquals(error, testCase.getRuntimeError().get().getErrorMessage());
        assertEquals("1", testCase.getInput());
        assertEquals("2", testCase.getExpectedOutput());
        assertFalse(testCase.getActualOutput().isPresent());
    }

    @Test
    void testEquals() {
        TestCaseResult successfulTestCaseResult1 = TestCaseResult.getSuccessfulTestCaseResult("1",
                " 2");
        TestCaseResult successfulTestCaseResult2 = TestCaseResult.getSuccessfulTestCaseResult("1",
                "3");
        TestCaseResult successfulTestCaseResult3 = TestCaseResult.getSuccessfulTestCaseResult("1",
                "3");

        TestCaseResult failedTestCaseResult1 = TestCaseResult.getFailedTestCaseResult("1", "2",
                "3");
        TestCaseResult failedTestCaseResult2 = TestCaseResult.getFailedTestCaseResult("6", "2",
                "4");
        TestCaseResult failedTestCaseResult3 = TestCaseResult.getFailedTestCaseResult("6", "2",
                "4");

        TestCaseResult erroredTestCaseResult1 = TestCaseResult.getErroredTestCaseResult("1", "2",
                "Error1");
        TestCaseResult erroredTestCaseResult2 = TestCaseResult.getErroredTestCaseResult("1", "2",
                "Error2");
        TestCaseResult erroredTestCaseResult3 = TestCaseResult.getErroredTestCaseResult("1", "2",
                "Error2");

        assertEquals(successfulTestCaseResult2, successfulTestCaseResult3);
        assertEquals(failedTestCaseResult2, failedTestCaseResult3);
        assertEquals(erroredTestCaseResult2, erroredTestCaseResult3);

        assertNotEquals(successfulTestCaseResult1, successfulTestCaseResult2);
        assertNotEquals(successfulTestCaseResult1, failedTestCaseResult1);

        assertNotEquals(failedTestCaseResult1, failedTestCaseResult2);
        assertNotEquals(failedTestCaseResult1, erroredTestCaseResult1);

        assertNotEquals(erroredTestCaseResult1, erroredTestCaseResult2);
        assertNotEquals(erroredTestCaseResult1, successfulTestCaseResult1);
    }
}
