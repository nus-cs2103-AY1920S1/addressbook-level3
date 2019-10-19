package com.dukeacademy.solutions;

import com.dukeacademy.model.question.TestCase;
import com.dukeacademy.model.solutions.TestExecutionResult;
import com.dukeacademy.model.solutions.UserProgram;
import com.dukeacademy.observable.Listener;
import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.StandardObservable;
import com.dukeacademy.solutions.compiler.Compiler;
import com.dukeacademy.solutions.compiler.StandardCompiler;
import com.dukeacademy.solutions.environment.CompilerEnvironment;
import com.dukeacademy.solutions.environment.StandardCompilerEnvironment;
import com.dukeacademy.solutions.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solutions.exceptions.ServiceInitializationException;
import com.dukeacademy.solutions.exceptions.ServiceNotInitializedException;
import com.dukeacademy.solutions.exceptions.TestExecutorException;
import com.dukeacademy.solutions.program.ProgramExecutor;
import com.dukeacademy.solutions.program.StandardProgramExecutor;
import com.dukeacademy.solutions.program.TestExecutorService;

import java.util.List;

public class StandardTestExecutorService implements TestExecutorService {
    private static final String messageInitializationError = "Failed to initialize standard test executor service.";

    private static StandardTestExecutorService standardTestExecutorService;
    private StandardObservable<TestExecutionResult> resultObservable;
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
    public void addTestExecutionResultListener(Listener<TestExecutionResult> listener) {
        this.resultObservable.addListener(listener);
    }

    @Override
    public boolean runTestCasesAgainstUserProgram(List<TestCase> testCases, UserProgram userProgram) {
        try {
            TestExecutionResult results = this.testExecutor.runTestCases(testCases, userProgram);
            this.resultObservable.setValue(results);
        } catch (TestExecutorException e) {
            return false;
        }

        return true;
    }
}
