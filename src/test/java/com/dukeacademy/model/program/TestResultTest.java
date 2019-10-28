package com.dukeacademy.model.program;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.dukeacademy.testexecutor.models.CompileError;

class TestResultTest {
    private final List<TestCaseResult> resultsSetA = List.of(new TestCaseResult[] {
            TestCaseResult.getSuccessfulTestCaseResult("1", "2"),
            TestCaseResult.getFailedTestCaseResult("2", "200", "300"),
            TestCaseResult.getErroredTestCaseResult("4", "20", "TestFailed"),
            TestCaseResult.getFailedTestCaseResult("2", "3", "1")
    });

    private final List<TestCaseResult> resultsSetB = List.of(new TestCaseResult[] {
            TestCaseResult.getSuccessfulTestCaseResult("70", "2"),
            TestCaseResult.getFailedTestCaseResult("2", "2278", "abc"),
            TestCaseResult.getErroredTestCaseResult("55", "007", "Bond"),
            TestCaseResult.getSuccessfulTestCaseResult("2", "3")
    });

    private final List<TestCaseResult> allSuccessResultSet = List.of(new TestCaseResult[] {
            TestCaseResult.getSuccessfulTestCaseResult("70", "2"),
            TestCaseResult.getSuccessfulTestCaseResult("70", "2"),
            TestCaseResult.getSuccessfulTestCaseResult("70", "2"),
            TestCaseResult.getSuccessfulTestCaseResult("70", "2")
    });

    @Test
    void testConstructorAndGetters() {
        TestResult success = new TestResult(allSuccessResultSet);
        assertTrue(success.isSuccessful());
        assertEquals(4, success.getNumPassed());
        assertEquals(allSuccessResultSet, success.getResults());
        assertFalse(success.getCompileError().isPresent());

        // Check immutability
        List<TestCaseResult> testCaseResults = new ArrayList<>();
        TestResult immutable = new TestResult(testCaseResults);
        testCaseResults.add(TestCaseResult.getErroredTestCaseResult("1", "1", "Error!"));
        assertEquals(immutable.getResults(), new ArrayList<>());

        TestResult fail = new TestResult(resultsSetA);
        assertFalse(fail.isSuccessful());
        assertEquals(1, fail.getNumPassed());
        assertEquals(resultsSetA, fail.getResults());
        assertFalse(fail.getCompileError().isPresent());

        CompileError error = new CompileError("This is a compile error!");
        TestResult compileError = new TestResult(error);
        assertFalse(compileError.isSuccessful());
        assertEquals(0, compileError.getNumPassed());
        assertEquals(compileError.getResults(), new ArrayList<>());
        assertTrue(compileError.getCompileError().isPresent());
        assertEquals(error, compileError.getCompileError().get());
    }

    @Test
    void testEquals() {
        TestResult resultA = new TestResult(resultsSetA);
        TestResult resultA2 = new TestResult(resultsSetA);
        assertEquals(resultA, resultA2);

        TestResult resultA3 = new TestResult(new ArrayList<>(resultsSetA));
        assertEquals(resultA, resultA3);

        TestResult resultB = new TestResult(resultsSetB);
        assertNotEquals(resultA, resultB);
    }
}
