package com.dukeacademy.testexecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.model.program.TestCaseResult;
import com.dukeacademy.model.program.TestResult;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.testexecutor.compiler.Compiler;
import com.dukeacademy.testexecutor.compiler.exceptions.CompilerException;
import com.dukeacademy.testexecutor.compiler.exceptions.CompilerFileContentException;
import com.dukeacademy.testexecutor.environment.CompilerEnvironment;
import com.dukeacademy.testexecutor.environment.exceptions.ClearEnvironmentException;
import com.dukeacademy.testexecutor.environment.exceptions.JavaFileCreationException;
import com.dukeacademy.testexecutor.exceptions.EmptyUserProgramException;
import com.dukeacademy.testexecutor.exceptions.IncorrectCanonicalNameException;
import com.dukeacademy.testexecutor.exceptions.TestExecutorException;
import com.dukeacademy.testexecutor.exceptions.TestExecutorExceptionWrapper;
import com.dukeacademy.testexecutor.executor.ProgramExecutor;
import com.dukeacademy.testexecutor.executor.exceptions.ProgramExecutorException;
import com.dukeacademy.testexecutor.models.ClassFile;
import com.dukeacademy.testexecutor.models.CompileError;
import com.dukeacademy.testexecutor.models.JavaFile;
import com.dukeacademy.testexecutor.models.ProgramInput;
import com.dukeacademy.testexecutor.models.ProgramOutput;

/**
 * Executes tests on user's written programs.
 */
public class TestExecutor {
    private static final String messageTestExecutorFailed = "Test executor failed unexpectedly.";
    private final Logger logger;
    private final CompilerEnvironment environment;
    private final Compiler compiler;
    private final ProgramExecutor executor;
    private final int timeLimit;

    /**
     * Instantiates a new Test executor.
     *
     * @param environment the environment
     * @param compiler    the compiler
     * @param executor    the executor
     */
    public TestExecutor(CompilerEnvironment environment, Compiler compiler, ProgramExecutor executor, int timeLimit) {
        this.environment = environment;
        this.compiler = compiler;
        this.executor = executor;
        this.timeLimit = timeLimit;
        this.logger = LogsCenter.getLogger(TestExecutor.class);
    }

    /**
     * Runs the user's program against a list of test cases. The UserProgram's sourceCode is required to match its
     * canonical name for the tests to be executed successfully. The results are packaged and returned as a TestResult
     * instance. The user's program specified canonical name and its source code must match or an exception will be
     * thrown. E.g. if the canonical name is dukeacademy.testexecutor.TestExecutor, it must
     * have the package statement "package dukeacademy.testexecutor" and it must contain an outer class
     * TestExecutor.
     *
     * @param testCases the test cases to be run.
     * @param program   the user's program.
     * @return a result instance.
     * @throws TestExecutorException           if the test executor fails unexpectedly.
     * @throws IncorrectCanonicalNameException if the canonical name of the UserProgram does not match its source code.
     * @throws EmptyUserProgramException       the empty user program exception
     */
    public TestResult runTestCases(List<TestCase> testCases, UserProgram program) throws TestExecutorException,
            IncorrectCanonicalNameException, EmptyUserProgramException {
        logger.info("TestCases received : " + testCases.toString().replaceAll("\n", ""));
        logger.info("User program received : " + program);

        // First check that the user's program is not empty
        if (program.getSourceCode().matches("\\s*")) {
            logger.warning("Empty user program received, tests will not be run");
            throw new EmptyUserProgramException();
        }

        try {
            // Compile the user's program
            ClassFile classFile = this.compileProgram(program);
            logger.info("Compilation succeeded, proceeding to run test cases...");

            // Initialize thread-safe List to collect results of test case execution
            CopyOnWriteArrayList<TestCaseResult> results = new CopyOnWriteArrayList<>();

            // List used to combine all CompletableFuture instances later on
            List<CompletableFuture<TestCaseResult>> completableFutureList = new ArrayList<>();

            for (TestCase testCase : testCases) {
                // Map each test case to the result of running them against the user's program
                CompletableFuture<TestCaseResult> evaluationTask = this.getEvaluationTask(classFile, testCase)
                        .whenCompleteAsync((testCaseResult, throwable) -> results.add(testCaseResult));

                completableFutureList.add(evaluationTask);
            }

            try {
                // Attempt to get the results of running each test case
                CompletableFuture.allOf(completableFutureList.toArray(CompletableFuture[]::new)).get();

                logger.info("Test execution completed. Test cases ran : " + results.size());
                return new TestResult(results);
            } catch (TestExecutorExceptionWrapper | InterruptedException | ExecutionException e) {
                logger.warning("Test execution failed unexpectedly. Aborting operation...");
                throw new TestExecutorException(e.getMessage());
            }

        } catch (CompilerFileContentException e) {
            return this.getTestExecutorResultWithCompileError(e.getCompileError());
        }

    }

    /**
     * Helper method to map the results of a program evaluation task to a TestCaseResult.
     *
     * @param classFile the ClassFile to be executed
     * @param testCase  the test case to be ran against
     * @return a CompletableFuture that returns a TestCaseResult
     * @throws TestExecutorException
     */
    private CompletableFuture<TestCaseResult> getEvaluationTask(ClassFile classFile, TestCase testCase)
            throws TestExecutorException {

        // Retrieve the necessary inputs/outputs
        ProgramInput input = new ProgramInput(testCase.getInput());
        String testCaseInput = testCase.getInput();
        String testCaseExpected = testCase.getExpectedResult();

        CompletableFuture<ProgramOutput> executionTask;

        // First attempt to retrieve the program execution task
        try {
            executionTask = executor.executeProgram(classFile, input);
        } catch (ProgramExecutorException e) {
            logger.warning("Test execution failed unexpectedly. Aborting operation...");
            throw new TestExecutorException(e.getMessage());
        }

        TestCaseResult erroredTestCaseResult = TestCaseResult.getErroredTestCaseResult(testCaseInput, testCaseExpected,
                "Time limit exceeded!");

        // Map the result of the execution task to a TestCaseResult upon completion and set it to timeout accordingly
        return executionTask
                .handleAsync((programOutput, throwable) -> getTestCaseResultFromProgramOutput(testCase, programOutput))
                .completeOnTimeout(erroredTestCaseResult, timeLimit, TimeUnit.SECONDS);
    }

    /**
     * Compiles the user program into a Java class file that can be executed.
     *
     * @param program the user's program
     * @return a Java class file.
     * @throws TestExecutorException           if the test executor fails unexpectedly.
     * @throws CompilerFileContentException    if the contents of the program is not compilable.
     * @throws IncorrectCanonicalNameException if the canonical name of the user program does not match its contents
     */
    private ClassFile compileProgram(UserProgram program) throws TestExecutorException, CompilerFileContentException,
            IncorrectCanonicalNameException {
        logger.info("Compiling program...");
        try {
            this.environment.clearEnvironment();
            JavaFile javaFile = this.environment.createJavaFile(program);
            return this.compiler.compileJavaFile(javaFile);
        } catch (CompilerException | JavaFileCreationException | ClearEnvironmentException e) {
            logger.warning("Compilation failed...");
            throw new TestExecutorException(messageTestExecutorFailed, e);
        }
    }

    private TestResult getTestExecutorResultWithCompileError(CompileError error) {
        return new TestResult(error);
    }

    private TestCaseResult getTestCaseResultFromProgramOutput(TestCase testcase, ProgramOutput output) {
        if (output.getRuntimeError().isPresent()) {
            String expected = testcase.getExpectedResult();
            String errorMessage = output.getRuntimeError().get().getErrorMessage();
            return TestCaseResult.getErroredTestCaseResult(testcase.getInput(), expected, errorMessage);
        }

        String input = testcase.getInput();
        String expected = testcase.getExpectedResult().replaceAll("(\n|\r\n)", System.lineSeparator());
        String actual = output.getOutput().replaceAll("\n", "\n");
        if (expected.equals(actual)) {
            return TestCaseResult.getSuccessfulTestCaseResult(input, actual);
        } else {
            return TestCaseResult.getFailedTestCaseResult(input, expected, actual);
        }
    }
}
