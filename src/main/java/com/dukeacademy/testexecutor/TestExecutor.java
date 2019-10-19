package com.dukeacademy.testexecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dukeacademy.model.question.TestCase;
import com.dukeacademy.model.solutions.TestCaseResult;
import com.dukeacademy.model.solutions.TestResult;
import com.dukeacademy.model.solutions.UserProgram;
import com.dukeacademy.testexecutor.compiler.Compiler;
import com.dukeacademy.testexecutor.environment.CompilerEnvironment;
import com.dukeacademy.testexecutor.exceptions.CompilerEnvironmentException;
import com.dukeacademy.testexecutor.exceptions.CompilerException;
import com.dukeacademy.testexecutor.exceptions.CompilerFileContentException;
import com.dukeacademy.testexecutor.exceptions.JavaFileCreationException;
import com.dukeacademy.testexecutor.exceptions.ProgramExecutorException;
import com.dukeacademy.testexecutor.exceptions.TestExecutorException;
import com.dukeacademy.testexecutor.exceptions.TestExecutorExceptionWrapper;
import com.dukeacademy.testexecutor.models.ClassFile;
import com.dukeacademy.testexecutor.models.CompileError;
import com.dukeacademy.testexecutor.models.JavaFile;
import com.dukeacademy.testexecutor.models.ProgramInput;
import com.dukeacademy.testexecutor.models.ProgramOutput;
import com.dukeacademy.testexecutor.program.ProgramExecutor;

/**
 * Executes tests on user's written programs.
 */
public class TestExecutor {
    private static final String messageTestExecutorFailed = "Test executor failed unexpectedly.";
    private static final String messageTestExecutorInstantiationFailed = "";

    private CompilerEnvironment environment;
    private Compiler compiler;
    private ProgramExecutor executor;

    public TestExecutor(CompilerEnvironment environment, Compiler compiler, ProgramExecutor executor) {
        this.environment = environment;
        this.compiler = compiler;
        this.executor = executor;
    }

    /**
     * Runs the user's program against a list of test cases.
     * @param testCases the test cases to be run.
     * @param program the user's program.
     * @return a result instance.
     * @throws TestExecutorException if the test executor fails unexpectedly.
     */
    public TestResult runTestCases(List<TestCase> testCases, UserProgram program) throws TestExecutorException {
        try {
            ClassFile classFile = this.compileProgram(program);

            try {
                List<TestCaseResult> results = testCases.parallelStream()
                        .map(testCase -> this.runIndividualTestCase(classFile, testCase))
                        .collect(Collectors.toList());

                return new TestResult(results);
            } catch (TestExecutorExceptionWrapper e) {
                throw new TestExecutorException(e.getMessage());
            }
        } catch (CompilerFileContentException e) {
            return this.getTestExecutorResultWithCompileError(e.getCompileError());
        }

    }

    /**
     * Compiles the user program into a Java class file that can be executed.
     * @param program the user's program
     * @return a Java class file.
     * @throws TestExecutorException if the test executor fails unexpectedly.
     * @throws CompilerFileContentException if the contents of the program is not compilable.
     */
    private ClassFile compileProgram(UserProgram program) throws TestExecutorException, CompilerFileContentException {
        try {
            this.environment.clearEnvironment();
            JavaFile javaFile = this.environment.createJavaFile(program);
            return this.compiler.compileJavaFile(javaFile);
        } catch (CompilerEnvironmentException | CompilerException | JavaFileCreationException e) {
            throw new TestExecutorException(messageTestExecutorFailed, e);
        }
    }

    /**
     * Runs the user's program against an individual test case.
     * @param program the user's compiled program.
     * @param testCase the test case to run the program against.
     * @return the results of the test case.
     * @throws TestExecutorExceptionWrapper
     */
    private TestCaseResult runIndividualTestCase(ClassFile program, TestCase testCase)
            throws TestExecutorExceptionWrapper {
        try {
            ProgramInput input = new ProgramInput(testCase.getInput());
            ProgramOutput output = this.executor.executeProgram(program, input);
            return getTestCaseResultFromProgramOutput(testCase, output);
        } catch (ProgramExecutorException e) {
            throw new TestExecutorExceptionWrapper(messageTestExecutorFailed);
        }
    }

    private TestResult getTestExecutorResultWithCompileError(CompileError error) {
        return new TestResult(new ArrayList<>(), error);
    }

    private TestCaseResult getTestCaseResultFromProgramOutput(TestCase testcase, ProgramOutput output) {
        if (output.getRuntimeError().isPresent()) {
            String expected = testcase.getExpectedResult();
            String errorMessage = output.getRuntimeError().get().getErrorMessage();
            return TestCaseResult.getErroredTestCaseResult(testcase.getInput(), expected, errorMessage);
        }

        String input = testcase.getInput();
        String expected = testcase.getExpectedResult();
        String actual = output.getOutput();
        if (expected.equals(actual)) {
            return TestCaseResult.getSuccessfulTestCaseResult(input, expected, actual);
        } else {
            return TestCaseResult.getFailedTestCaseResult(input, expected, actual);
        }
    }
}
