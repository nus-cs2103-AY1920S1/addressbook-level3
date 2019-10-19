package com.dukeacademy.logic;

import com.dukeacademy.model.question.TestCase;
import com.dukeacademy.model.program.TestResult;
import com.dukeacademy.model.program.UserProgram;
import com.dukeacademy.observable.Observable;

import java.util.List;

public interface ProgramSubmissionLogic {
    public Observable<TestResult> getTestResultObservable();

    public boolean runTestCasesAgainstUserProgram(List<TestCase> testCases, UserProgram userProgram);
}
