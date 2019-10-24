package com.dukeacademy.testexecutor;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.dukeacademy.model.program.TestCaseResult;
import com.dukeacademy.model.program.TestResult;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.testexecutor.compiler.Compiler;
import com.dukeacademy.testexecutor.environment.CompilerEnvironment;
import com.dukeacademy.testexecutor.exceptions.CompilerEnvironmentException;
import com.dukeacademy.testexecutor.exceptions.CompilerException;
import com.dukeacademy.testexecutor.exceptions.CompilerFileContentException;
import com.dukeacademy.testexecutor.exceptions.EmptyUserProgramException;
import com.dukeacademy.testexecutor.exceptions.IncorrectClassNameException;
import com.dukeacademy.testexecutor.exceptions.JavaFileCreationException;
import com.dukeacademy.testexecutor.exceptions.ProgramExecutorException;
import com.dukeacademy.testexecutor.exceptions.TestExecutorException;
import com.dukeacademy.testexecutor.exceptions.TestExecutorExceptionWrapper;
import com.dukeacademy.testexecutor.executor.ProgramExecutor;
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
     *
     * @param testCases the test cases to be run.
     * @param program   the user's program.
     * @return a result instance.
     * @throws TestExecutorException if the test executor fails unexpectedly.
     */
    public TestResult runTestCases(List<TestCase> testCases, UserProgram program) throws TestExecutorException,
            IncorrectClassNameException, EmptyUserProgramException {

        if (program.getSourceCode().equals("")) {
            throw new EmptyUserProgramException();
        }

        if (!this.checkClassNameMatch(program)) {
            throw new IncorrectClassNameException();
        }
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
     *
     * @param program the user's program
     * @return a Java class file.
     * @throws TestExecutorException        if the test executor fails unexpectedly.
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
     *
     * @param program  the user's compiled program.
     * @param testCase the test case to run the program against.
     * @return the results of the test case.
     * @throws TestExecutorExceptionWrapper when the test executor fails unexpectedly
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
        String actual = output.getOutput();
        if (expected.equals(actual)) {
            return TestCaseResult.getSuccessfulTestCaseResult(input, expected, actual);
        } else {
            return TestCaseResult.getFailedTestCaseResult(input, expected, actual);
        }
    }

    /**
     * Helper function to check if the program's source code has an outer class that matches the specified class name,
     * @param program the program to be checked.
     * @return true if the program has a matching outer class.
     */
    public boolean checkClassNameMatch(UserProgram program) {
        String sourceCode = program.getSourceCode();
        String targetClassName = program.getClassName();

        // Check if there is an outer class matching the target class name
        String[] split = sourceCode.split("class " + targetClassName);

        if (split.length == 1) {
            return false;
        }

        for (int i = 1; i < split.length; i++) {
            Stack<Character> braceStack = new Stack<>();

            boolean valid = true;
            for (char c : split[i].toCharArray()) {
                if (c == '{') {
                    braceStack.push('{');
                }

                if (c == '}') {
                    if (braceStack.size() == 0) {
                        valid = false;
                        break;
                    }
                    braceStack.pop();
                }
            }

            if (valid && braceStack.size() == 0) {
                return true;
            }
        }

        return false;
    }
}
