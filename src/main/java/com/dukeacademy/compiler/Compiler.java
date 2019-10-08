package com.dukeacademy.compiler;

import com.dukeacademy.compiler.environment.CompilerEnvironment;
import com.dukeacademy.compiler.exceptions.CompilerContentException;
import com.dukeacademy.compiler.exceptions.CompilerEnvironmentException;
import com.dukeacademy.compiler.exceptions.CompilerCompileException;
import com.dukeacademy.compiler.exceptions.FileCreationException;
import com.dukeacademy.model.TestCase;
import com.dukeacademy.model.UserProgram;
import com.dukeacademy.model.compiler.TestCaseResult;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.List;

public class Compiler {
    private static final String MESSAGE_COMPILER_COMPILE_FAILED = "Compiler failed to compile the source code";

    private CompilerEnvironment environment;
    private JavaCompiler javaCompiler;

    public Compiler(CompilerEnvironment environment) {
        this.environment = environment;
        this.javaCompiler = ToolProvider.getSystemJavaCompiler();
    }

    public List<TestCaseResult> runTestCases(List<TestCase> testCase, UserProgram userProgram) throws CompilerCompileException {
        List<TestCaseResult> results = new ArrayList<>();

        return results;
    }

    private void compileProgram(UserProgram program) throws CompilerCompileException {
        try {
            environment.clearEnvironment();

            String className = program.getClassName();
            String code = program.getSourceCodeAsString();

            environment.createJavaFile(className, code);

            // TODO: compile program with proper error handling

        } catch (CompilerEnvironmentException | FileCreationException | CompilerContentException e) {
            throw new CompilerCompileException(MESSAGE_COMPILER_COMPILE_FAILED, e);
        }
    }

    private void executeTestCase(String className, TestCase testCase) {
        // TODO: implement testcase execution
    }
}
