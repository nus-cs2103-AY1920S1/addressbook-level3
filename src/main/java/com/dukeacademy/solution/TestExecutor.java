package com.dukeacademy.solution;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.solution.exceptions.ProgramExecutorException;
import com.dukeacademy.solution.exceptions.TestExecutorExceptionWrapper;
import com.dukeacademy.solution.models.CompileError;
import com.dukeacademy.model.solution.TestCase;
import com.dukeacademy.model.solution.TestCaseResult;
import com.dukeacademy.model.solution.TestExecutorResult;
import com.dukeacademy.model.solution.UserProgram;
import com.dukeacademy.solution.compiler.Compiler;
import com.dukeacademy.solution.environment.CompilerEnvironment;
import com.dukeacademy.solution.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solution.exceptions.CompilerException;
import com.dukeacademy.solution.exceptions.CompilerFileContentException;
import com.dukeacademy.solution.exceptions.JavaFileCreationException;
import com.dukeacademy.solution.exceptions.TestExecutorException;
import com.dukeacademy.solution.models.ClassFile;
import com.dukeacademy.solution.models.JavaFile;
import com.dukeacademy.solution.models.ProgramInput;
import com.dukeacademy.solution.models.ProgramOutput;
import com.dukeacademy.solution.program.ProgramExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TestExecutor {
    private static final String messageTestExecutorFailed = "Test executor failed unexpectedly.";

    private CompilerEnvironment environment;
    private Compiler compiler;
    private ProgramExecutor executor;
    private Logger logger;

    public TestExecutor(CompilerEnvironment environment, Compiler compiler, ProgramExecutor executor) {
        this.environment = environment;
        this.compiler = compiler;
        this.executor = executor;
        this.logger = LogsCenter.getLogger(TestExecutor.class);
    }

    public TestExecutorResult runTestCases(List<TestCase> testCases, UserProgram program) throws TestExecutorException {
        try {
            ClassFile classFile = this.compileProgram(program);

            try {
                List<TestCaseResult> results = testCases.parallelStream()
                        .map(testCase -> this.runIndividualTestCase(classFile, testCase))
                        .collect(Collectors.toList());

                return new TestExecutorResult(results);
            } catch (TestExecutorExceptionWrapper e) {
                throw new TestExecutorException(e.getMessage());
            }
        } catch (CompilerFileContentException e) {
            return this.getTestExecutorResultWithCompileError(e.getCompileError());
        }

    }

    private ClassFile compileProgram(UserProgram program) throws TestExecutorException, CompilerFileContentException {
        try {
            this.environment.clearEnvironment();
            JavaFile javaFile = this.environment.createJavaFile(program);
            return this.compiler.compileJavaFile(javaFile);
        } catch (CompilerEnvironmentException | CompilerException | JavaFileCreationException e) {
            throw new TestExecutorException(messageTestExecutorFailed, e);
        }
    }

    private TestCaseResult runIndividualTestCase(ClassFile program, TestCase testCase) throws TestExecutorExceptionWrapper {
        try {
            ProgramInput input = new ProgramInput(testCase.getInput());
            ProgramOutput output = this.executor.executeProgram(program, input);
            return getTestCaseResultFromProgramOutput(testCase, output);
        } catch (ProgramExecutorException e) {
            throw new TestExecutorExceptionWrapper(messageTestExecutorFailed);
        }
    }

    private TestExecutorResult getTestExecutorResultWithCompileError(CompileError error) {
        return new TestExecutorResult(new ArrayList<>(), error);
    }

    private TestCaseResult getTestCaseResultFromProgramOutput(TestCase testcase, ProgramOutput output) {
        String expected = testcase.getExpectedResult();
        String actual = output.getOutput();

        if (expected.equals(actual)) {
            return new TestCaseResult(true, expected, actual);
        } else {
            return new TestCaseResult(false, expected, actual);
        }
    }
}
