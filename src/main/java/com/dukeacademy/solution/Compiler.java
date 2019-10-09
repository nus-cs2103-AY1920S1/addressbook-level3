package com.dukeacademy.solution;

import com.dukeacademy.solution.exceptions.UserProgramException;
import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.solution.environment.CompilerEnvironment;
import com.dukeacademy.solution.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solution.exceptions.CompilerException;
import com.dukeacademy.solution.exceptions.CompilerFileCreationException;
import com.dukeacademy.model.solution.UserProgram;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Compiler {
    private static final String MESSAGE_COMPILER_FAILED = "Compiler failed.";
    private static final String MESSAGE_FAILED_TO_CLEAR_ENVIRONMENT = "Failed to clear the compiler environment. "
            + "Generated files and classes may persist.";

    private static final Logger logger = LogsCenter.getLogger(Compiler.class);
    private CompilerEnvironment environment;
    private JavaCompiler javaCompiler;

    public Compiler(CompilerEnvironment environment) {
        this.javaCompiler = ToolProvider.getSystemJavaCompiler();
        this.environment = environment;
    }

    public void close() {
        try {
            this.environment.close();
        } catch (CompilerEnvironmentException e) {
            logger.fine(MESSAGE_FAILED_TO_CLEAR_ENVIRONMENT);
        }
    }

    public void compileProgram(UserProgram program) throws CompilerException, UserProgramException {
        try {
            environment.clearEnvironment();

            String className = program.getClassName();
            String code = program.getSourceCodeAsString();

            File javaFile = environment.createJavaFile(className, code);

            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(diagnostics, null, null);
            Iterable<? extends JavaFileObject> sources = fileManager.getJavaFileObjects(javaFile);

            JavaCompiler.CompilationTask compilationTask = javaCompiler.getTask(null, fileManager, diagnostics, null, null, sources);
            compilationTask.call();

            List<Diagnostic<? extends JavaFileObject>> errors = diagnostics.getDiagnostics();

            Optional<Diagnostic<? extends JavaFileObject>> error = errors.stream().findFirst();
            if (error.isPresent()) {
                String errorMessage = error.map(diagnostic -> diagnostic.getMessage(null)).get();
                this.clearEnvironmentAfterUserProgramInvalid(errorMessage);
                throw new UserProgramException(errorMessage);
            }

        } catch (CompilerFileCreationException | CompilerEnvironmentException e) {
            this.clearEnvironmentAfterCompilerFail();
            throw new CompilerException(MESSAGE_COMPILER_FAILED, e);
        }
    }

    private void clearEnvironmentAfterUserProgramInvalid(String errorMessage) throws UserProgramException {
        try {
            this.environment.clearEnvironment();
        } catch (CompilerEnvironmentException e) {
            throw new UserProgramException(errorMessage + " " + MESSAGE_FAILED_TO_CLEAR_ENVIRONMENT);
        }
    }

    private void clearEnvironmentAfterCompilerFail() throws CompilerException {
        try {
            this.environment.clearEnvironment();
        } catch (CompilerEnvironmentException e) {
            throw new CompilerException(MESSAGE_COMPILER_FAILED + " " + MESSAGE_FAILED_TO_CLEAR_ENVIRONMENT);
        }
    }
}
