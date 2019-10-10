package com.dukeacademy.solution;

import com.dukeacademy.solution.exceptions.CompilerFileContentException;
import com.dukeacademy.solution.exceptions.CompilerException;
import com.dukeacademy.solution.models.ClassFile;
import com.dukeacademy.solution.models.JavaFile;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public class Compiler {
    private static final String MESSAGE_COMPILER_FAILED = "Compiler failed.";
    private JavaCompiler javaCompiler;

    public Compiler() {
        this.javaCompiler = ToolProvider.getSystemJavaCompiler();
    }

    public ClassFile compileProgram(JavaFile javaFile) throws CompilerException, CompilerFileContentException {
        try {
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(diagnostics, null, null);
            Iterable<? extends JavaFileObject> sources = fileManager.getJavaFileObjects(javaFile.getFile());

            JavaCompiler.CompilationTask compilationTask = javaCompiler.getTask(null, fileManager, diagnostics, null, null, sources);
            compilationTask.call();

            List<Diagnostic<? extends JavaFileObject>> errors = diagnostics.getDiagnostics();

            Optional<Diagnostic<? extends JavaFileObject>> error = errors.stream().findFirst();
            if (error.isPresent()) {
                String errorMessage = error.map(diagnostic -> diagnostic.getMessage(null)).get();
                throw new CompilerFileContentException(errorMessage);
            }

            return new ClassFile(javaFile.getCanonicalName(), javaFile.getClassPath());
        } catch (FileNotFoundException e) {
            throw new CompilerException(MESSAGE_COMPILER_FAILED, e);
        }
    }
}
