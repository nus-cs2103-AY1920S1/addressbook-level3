package com.dukeacademy.model.solutions;

import java.util.List;
import java.util.Optional;

import com.dukeacademy.testexecutor.models.CompileError;

/**
 * Represents the results of running a user's solution against the specified test cases
 */
public class TestResult {
    private final List<TestCaseResult> results;
    private final long numPassed;
    private final Optional<CompileError> compileError;

    public TestResult(List<TestCaseResult> results) {
        this.results = results;
        this.numPassed = results.parallelStream().filter(TestCaseResult::isSuccessful).count();
        this.compileError = Optional.empty();
    }

    public TestResult(List<TestCaseResult> results, CompileError compileError) {
        this.results = results;
        this.numPassed = results.parallelStream().filter(TestCaseResult::isSuccessful).count();
        this.compileError = Optional.of(compileError);
    }

    public List<TestCaseResult> getResults() {
        return results;
    }

    public long getNumPassed() {
        return numPassed;
    }

    public Optional<CompileError> getCompileError() {
        return compileError;
    }
}
