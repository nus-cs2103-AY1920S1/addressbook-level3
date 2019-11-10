package com.dukeacademy.testexecutor.compiler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.testexecutor.compiler.exceptions.CompilerException;
import com.dukeacademy.testexecutor.compiler.exceptions.CompilerFileContentException;
import com.dukeacademy.testexecutor.compiler.exceptions.EmptyJavaFileException;
import com.dukeacademy.testexecutor.models.ClassFile;
import com.dukeacademy.testexecutor.models.JavaFile;

/**
 * The standard implementation of the Compiler interface. This class utilizes Java Standard Library's JavaCompiler class
 * in order to compile Java files programmatically.
 */
public class StandardCompiler implements Compiler {
    private static final String messageRetrieveJavaFileFailed = "Compiler could not retrieve Java file : ";
    private static final String messageCompilerFailedUnexpected = "Compiler failed unexpectedly";

    private final JavaCompiler javaCompiler;
    private final Logger logger = LogsCenter.getLogger(StandardCompiler.class);


    /**
     * Instantiates a new Standard compiler.
     */
    public StandardCompiler() {
        this.javaCompiler = ToolProvider.getSystemJavaCompiler();
    }

    @Override
    public ClassFile compileJavaFile(JavaFile javaFile) throws CompilerException, CompilerFileContentException {
        try {
            String content = Files.readString(javaFile.getFile().toPath());
            if ("".equals(content)) {
                throw new EmptyJavaFileException();
            }
        } catch (IOException e) {
            logger.warning(messageCompilerFailedUnexpected);
            logger.warning(e.getMessage());
            throw new CompilerException(e.getMessage(), e);
        }

        try {
            logger.info("Compiling java file : " + javaFile);
            Iterable<? extends JavaFileObject> sources = this.getJavaFileObjects(javaFile);
            this.compileJavaFiles(sources);

            logger.info("Compilation complete : " + javaFile);

            // If compilation is successful, the class file produced will be in the same folder with the same
            // canonical name and class path
            ClassFile result = new ClassFile(javaFile.getCanonicalName(), javaFile.getClassPath());
            logger.info("Successfully retrieved generated Class file : " + result);
            return result;
        } catch (FileNotFoundException e) {
            logger.warning(messageCompilerFailedUnexpected);
            logger.warning(e.getMessage());
            throw new CompilerException(e.getMessage(), e);
        }
    }

    /**
     * Retrieves JavaFiles as an iterable of JavaFileObjects object used by the JavaCompiler class for compilation
     * @param javaFile an instance of the JavaFile model class
     * @return an iterable of JavaFileObjects
     * @throws CompilerException if the file cannot be retrieved
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
            logger.warning(messageRetrieveJavaFileFailed + javaFile);
            throw new CompilerException(messageRetrieveJavaFileFailed + javaFile);
        }

        return sources;
    }

    /**
     * Compiles JavaFileObject sources into actual Class files using the JavaCompiler class.
     * @param sources an iterable of JavaFileObjects that can be compiled by the compiler
     * @throws CompilerFileContentException if a compilation error occurs
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
                    .map(Object::toString)
                    .reduce((m1, m2) -> m1 + "\n" + m2);

        if (errorMessages.isPresent()) {
            throw new CompilerFileContentException(errorMessages.get());
        }
    }
}
