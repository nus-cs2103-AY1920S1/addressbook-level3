package com.dukeacademy.solution.compiler;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.dukeacademy.solution.exceptions.CompilerException;
import com.dukeacademy.solution.exceptions.CompilerFileContentException;
import com.dukeacademy.solution.models.ClassFile;
import com.dukeacademy.solution.models.JavaFile;

/**
 * Standard implementation of the Compiler interface.
 */
public class StandardCompiler implements Compiler {
    private static final String MESSAGE_COMPILER_FAILED = "Compiler failed.";
    private JavaCompiler javaCompiler;

    public StandardCompiler() {
        this.javaCompiler = ToolProvider.getSystemJavaCompiler();
    }

    @Override
    public ClassFile compileJavaFile(JavaFile javaFile) throws CompilerException, CompilerFileContentException {
        try {
            Iterable<? extends JavaFileObject> sources = this.getJavaFileObjects(javaFile);
            this.compileJavaFiles(sources);
            return new ClassFile(javaFile.getCanonicalName(), javaFile.getClassPath());
        } catch (FileNotFoundException e) {
            throw new CompilerException(MESSAGE_COMPILER_FAILED + " Could not retrieve Class file.");
        }
    }

    /**
     * Retrieves JavaFiles as an iterable of JavaFileObjects object used by the JavaCompiler class for class
     * compilation during runtime.
     * @param javaFile an instance of the application's own JavaFile instance.
     * @return an iterable of JavaFileObjects
     * @throws CompilerException if the file cannot be retrieved.
     */
    private Iterable<? extends JavaFileObject> getJavaFileObjects(JavaFile javaFile) throws CompilerException {
        // DiagnosticsCollector is used to listen for non-fatal diagnostics
        DiagnosticCollector<JavaFileObject> fileManagerDiagnostics = new DiagnosticCollector<>();

        // Standard file manager used to get JavaFileObjects
        StandardJavaFileManager fileManager = javaCompiler
                .getStandardFileManager(fileManagerDiagnostics, null, null);

        // Retrieve our Java file as a JavaFileObject
        Iterable<? extends JavaFileObject> sources = fileManager.getJavaFileObjects(javaFile.getFile());

        // Ensure that the DiagnosticsCollector did not collect any errors
        List<Diagnostic<? extends JavaFileObject>> errors = fileManagerDiagnostics.getDiagnostics();
        if (errors.size() > 0) {
            throw new CompilerException(MESSAGE_COMPILER_FAILED + " Could not retrieve Java file.");
        }

        return sources;
    }

    /**
     * Compiles JavaFileObject sources into actual Class files that can be executed by the Java Runtime using the
     * JavaCompiler class.
     * @param sources an iterable of JavaFileObjects that can be compiled by the compiler.
     * @throws CompilerFileContentException if the contents of the Java file leads to a compilation error.
     */
    private void compileJavaFiles(Iterable<? extends JavaFileObject> sources) throws CompilerFileContentException {
        // DiagnosticCollector used to listen for non-fatal diagnostics from the compiler
        DiagnosticCollector<JavaFileObject> compilerDiagnostics = new DiagnosticCollector<>();

        // Standard file manager used to write newly compiled artifacts
        StandardJavaFileManager fileManager = javaCompiler
                .getStandardFileManager(compilerDiagnostics, null, null);

        // Get an asynchronous task representing the compilation of the source Java files
        JavaCompiler.CompilationTask compilationTask = javaCompiler
                .getTask(null, fileManager, compilerDiagnostics, null, null, sources);

        // Start the compilation task
        compilationTask.call();

        // Ensure that the DiagnosticCollector did not collect any errors
        List<Diagnostic<? extends JavaFileObject>> errors = compilerDiagnostics.getDiagnostics();
        Optional<String> errorMessages = errors.stream()
                    .map(diagnostic -> diagnostic.getMessage(null))
                    .reduce((m1, m2) -> m1 + "\n" + m2);


        if (errorMessages.isPresent()) {
            throw new CompilerFileContentException(errorMessages.get());
        }
    }


}
