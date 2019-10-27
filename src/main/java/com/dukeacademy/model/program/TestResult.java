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


    /**
     * Instantiates a new Test executor result.
     *
     * @param results the results
     */
    public TestResult(List<TestCaseResult> results) {
        requireNonNull(results);

        this.results = new ArrayList<>(results);
        this.numPassed = results.parallelStream().filter(TestCaseResult::isSuccessful).count();
        this.compileError = Optional.empty();
    }

    /**
     * Instantiates a new Test executor result.
     *
     * @param compileError the compile error
     */
    public TestResult(CompileError compileError) {
        this.results = new ArrayList<>();
        this.numPassed = 0;
        this.compileError = Optional.of(compileError);
    }

    /**
     * Returns if the test result was successful.
     *
     * @return true if the test was successful
     */
    public boolean isSuccessful() {
        if (this.compileError.isPresent()) {
            return false;
        }

        return this.numPassed == results.size();
    }

    /**
     * Gets results.
     *
     * @return the results
     */
    public List<TestCaseResult> getResults() {
        return results;
    }

    /**
     * Gets num passed.
     *
     * @return the num passed
     */
    public long getNumPassed() {
        return numPassed;
    }

    /**
     * Gets compile error.
     *
     * @return the compile error
     */
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
