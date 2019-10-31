package com.dukeacademy.testexecutor;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    /**
     * Instantiates a new Test executor.
     *
     * @param environment the environment
     * @param compiler    the compiler
     * @param executor    the executor
     */
    public TestExecutor(CompilerEnvironment environment, Compiler compiler, ProgramExecutor executor) {
        this.environment = environment;
        this.compiler = compiler;
        this.executor = executor;
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

        if (program.getSourceCode().matches("\\s*")) {
            logger.warning("Empty user program received, tests will not be run");
            throw new EmptyUserProgramException();
        }

        try {
            ClassFile classFile = this.compileProgram(program);
            logger.info("Compilation succeeded, proceeding to run test cases...");

            try {
                List<TestCaseResult> results = testCases.parallelStream()
                        .map(testCase -> this.runIndividualTestCase(classFile, testCase))
                        .collect(Collectors.toList());

                logger.info("Test execution completed. Test cases ran : " + results.size());
                return new TestResult(results);
            } catch (TestExecutorExceptionWrapper e) {
                logger.warning("Test execution failed unexpectedly. Aborting operation...");
                throw new TestExecutorException(e.getMessage());
            }
        } catch (CompilerFileContentException e) {
            return this.getTestExecutorResultWithCompileError(e.getCompileError());
        }

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

    /**
     * Runs the user's program against an individual test case.
     *
     * @param program  the user's compiled program.
     * @param testCase the test case to run the program against.
     * @return the results of the test case.
     * @throws TestExecutorExceptionWrapper when the test executor fails unexpectedly
     */
    private TestCaseResult runIndividualTestCase(ClassFile program, TestCase testCase)
            throws TestExecutorExceptionWrapper {
        try {
            logger.info("Running test case : " + testCase);
            ProgramInput input = new ProgramInput(testCase.getInput());
            ProgramOutput output = this.executor.executeProgram(program, input);
            return getTestCaseResultFromProgramOutput(testCase, output);
        } catch (ProgramExecutorException e) {
            throw new TestExecutorExceptionWrapper(messageTestExecutorFailed);
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
        String expected = testcase.getExpectedResult();
        String actual = output.getOutput().replaceAll("\n", "\n");
        if (expected.equals(actual)) {
            return TestCaseResult.getSuccessfulTestCaseResult(input, actual);
        } else {
            return TestCaseResult.getFailedTestCaseResult(input, expected, actual);
        }
    }
}
