package com.dukeacademy.compiler;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.compiler.environment.CompilerEnvironment;
import com.dukeacademy.compiler.exceptions.CompilerContentException;
import com.dukeacademy.compiler.exceptions.CompilerEnvironmentException;
import com.dukeacademy.compiler.exceptions.CompilerCompileException;
import com.dukeacademy.compiler.exceptions.FileCreationException;
import com.dukeacademy.model.TestCase;
import com.dukeacademy.model.UserProgram;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class Compiler {
    private static final String MESSAGE_COMPILER_COMPILE_FAILED = "Compiler failed to compile the source code";
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

    public void compileProgram(UserProgram program) throws CompilerCompileException {
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

            List<Diagnostic<? extends JavaFileObject>> results = diagnostics.getDiagnostics();
            // TODO: compile program with proper error handling
        } catch (FileCreationException | CompilerEnvironmentException e) {
            throw new CompilerCompileException(MESSAGE_COMPILER_COMPILE_FAILED, e);
        }
    }
}
