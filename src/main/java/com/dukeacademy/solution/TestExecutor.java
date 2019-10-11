package com.dukeacademy.solution;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.model.solution.TestCase;
import com.dukeacademy.model.solution.TestCaseResult;
import com.dukeacademy.model.solution.UserProgram;
import com.dukeacademy.solution.compiler.Compiler;
import com.dukeacademy.solution.environment.CompilerEnvironment;
import com.dukeacademy.solution.models.ClassFile;
import com.dukeacademy.solution.models.JavaFile;
import com.dukeacademy.solution.models.ProgramInput;
import com.dukeacademy.solution.models.ProgramOutput;
import com.dukeacademy.solution.program.ProgramExecutor;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TestExecutor {
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

    public List<TestCaseResult> runTestCases(List<TestCase> testCases, UserProgram program) {
        ClassFile classFile = this.compileProgram(program);

        return testCases.parallelStream()
                .map(testCase -> this.runIndividualTestCase(classFile, testCase))
                .collect(Collectors.toList());
    }

    private ClassFile compileProgram(UserProgram program) {
        try {
            this.environment.clearEnvironment();
            JavaFile javaFile = this.environment.createJavaFile(program);
            return this.compiler.compileJavaFile(javaFile);
        } catch (Exception e) {
            // TODO: proper exception handling
            e.printStackTrace();
            return null;
        }
    }

    private TestCaseResult runIndividualTestCase(ClassFile program, TestCase testCase) {
        try {
            ProgramInput input = new ProgramInput(testCase.getInput());
            ProgramOutput output = this.executor.executeProgram(program, input);

            return getTestCaseResultFromProgramOutput(testCase, output);
        } catch (Exception e) {
            // TODO: proper exception handling
            e.printStackTrace();
            return null;
        }
    }

    private TestCaseResult getTestCaseResultFromProgramOutput(TestCase testcase, ProgramOutput output) {
        String expected = testcase.getExpectedResult();
        String actual = output.getOutput();

        if (expected.equals(output)) {
            return new TestCaseResult(true, expected, actual);
        } else {
            return new TestCaseResult(false, expected, actual);
        }
    }
}
