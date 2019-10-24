package com.dukeacademy.model.program;

import java.util.ArrayList;
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

    public TestResult(CompileError compileError) {
        this.results = new ArrayList<>();
        this.numPassed = 0;
        this.compileError = Optional.of(compileError);
    }

    public boolean isSuccessful() {
        return this.numPassed == results.size();
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof TestResult) {
            return ((TestResult) o).results.equals(this.results)
                    && ((TestResult) o).numPassed == this.numPassed
                    && ((TestResult) o).compileError.equals(this.compileError);
        } else {
            return false;
        }
    }
}
