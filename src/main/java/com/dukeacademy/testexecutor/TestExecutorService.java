package com.dukeacademy.testexecutor;

import com.dukeacademy.model.question.TestCase;
import com.dukeacademy.model.solutions.TestResult;
import com.dukeacademy.model.solutions.UserProgram;
import com.dukeacademy.observable.Listener;
import com.dukeacademy.observable.Observable;

import java.util.List;

public interface TestExecutorService {
    public Observable<TestResult> getTestResultObservable();

    public boolean runTestCasesAgainstUserProgram(List<TestCase> testCases, UserProgram userProgram);
}
