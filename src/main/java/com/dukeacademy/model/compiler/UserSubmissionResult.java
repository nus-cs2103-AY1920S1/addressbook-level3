package com.dukeacademy.model.compiler;

import java.util.List;

/**
 * Represents the results of submitting a user's solution.
 */
public class UserSubmissionResult {
    private List<TestCaseResult> testCaseResultList;

    public List<TestCaseResult> getTestCaseResultList() {
        return this.testCaseResultList;
    }
}
