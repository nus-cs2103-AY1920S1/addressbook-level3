package com.dukeacademy.solutions.program;

import com.dukeacademy.model.question.TestCase;
import com.dukeacademy.model.solutions.TestExecutionResult;
import com.dukeacademy.model.solutions.UserProgram;
import com.dukeacademy.observable.Listener;

import java.util.List;

public interface TestExecutorService {
    public void addTestExecutionResultListener(Listener<TestExecutionResult> listener);

    public boolean runTestCasesAgainstUserProgram(List<TestCase> testCases, UserProgram userProgram);
}
