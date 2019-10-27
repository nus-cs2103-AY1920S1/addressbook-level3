package com.dukeacademy.model.program;

import static java.util.Objects.requireNonNull;

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
        requireNonNull(results);

        this.results = new ArrayList<>(results);
        this.numPassed = results.parallelStream().filter(TestCaseResult::isSuccessful).count();
        this.compileError = Optional.empty();
    }

    public TestResult(CompileError compileError) {
        this.results = new ArrayList<>();
        this.numPassed = 0;
        this.compileError = Optional.of(compileError);
    }

    /**
     * Returns if the test result was successful.
     * @return true if the test was successful
     */
    public boolean isSuccessful() {
        if (this.compileError.isPresent()) {
            return false;
        }

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
    public boolean equals(Object object) {
        if (object instanceof TestResult) {
            TestResult other = (TestResult) object;
            return other.numPassed == this.numPassed
                    && other.results.equals(this.results)
                    && other.getCompileError().equals(this.getCompileError());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Test result:\n"
                + "Successful : " + this.isSuccessful() + "\n"
                + "Compile error : " + this.getCompileError().isPresent() + "\n"
                + "Test cases run : " + this.getResults().size();
    }
}
