package com.dukeacademy.testexecutor;

import com.dukeacademy.model.question.TestCase;
import com.dukeacademy.model.solutions.TestResult;
import com.dukeacademy.model.solutions.UserProgram;
import com.dukeacademy.observable.Listener;
import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.StandardObservable;
import com.dukeacademy.testexecutor.compiler.Compiler;
import com.dukeacademy.testexecutor.compiler.StandardCompiler;
import com.dukeacademy.testexecutor.environment.CompilerEnvironment;
import com.dukeacademy.testexecutor.environment.StandardCompilerEnvironment;
import com.dukeacademy.testexecutor.exceptions.CompilerEnvironmentException;
import com.dukeacademy.testexecutor.exceptions.ServiceInitializationException;
import com.dukeacademy.testexecutor.exceptions.ServiceNotInitializedException;
import com.dukeacademy.testexecutor.exceptions.TestExecutorException;
import com.dukeacademy.testexecutor.program.ProgramExecutor;
import com.dukeacademy.testexecutor.program.StandardProgramExecutor;

import java.util.List;

public class StandardTestExecutorService implements TestExecutorService {
    private static final String messageInitializationError = "Failed to initialize standard test executor service.";

    private static StandardTestExecutorService standardTestExecutorService;
    private StandardObservable<TestResult> resultObservable;
    private CompilerEnvironment compilerEnvironment;
    private TestExecutor testExecutor;

    public static void initializeTestExecutorService(String outputDirectoryPath) throws ServiceInitializationException {
        try {
            CompilerEnvironment environment = new StandardCompilerEnvironment(outputDirectoryPath);
            Compiler compiler = new StandardCompiler();
            ProgramExecutor executor = new StandardProgramExecutor();

            StandardTestExecutorService.standardTestExecutorService = new StandardTestExecutorService(environment,
                    compiler, executor);
        } catch (CompilerEnvironmentException e) {
            throw new ServiceInitializationException(messageInitializationError, e);
        }
    }

    public static StandardTestExecutorService getStandardTestExecutorService() throws ServiceNotInitializedException {
        if (standardTestExecutorService == null) {
            throw new ServiceNotInitializedException();
        }

        return StandardTestExecutorService.standardTestExecutorService;
    }

    private StandardTestExecutorService(CompilerEnvironment environment, Compiler compiler, ProgramExecutor executor) {
        this.compilerEnvironment = environment;
        this.testExecutor = new TestExecutor(environment, compiler, executor);
        this.resultObservable = new StandardObservable<>();
    }

    @Override
    public Observable<TestResult> getTestResultObservable() {
        return this.resultObservable;
    }

    @Override
    public boolean runTestCasesAgainstUserProgram(List<TestCase> testCases, UserProgram userProgram) {
        try {
            TestResult results = this.testExecutor.runTestCases(testCases, userProgram);
            this.resultObservable.setValue(results);
        } catch (TestExecutorException e) {
            return false;
        }

        return true;
    }
}
