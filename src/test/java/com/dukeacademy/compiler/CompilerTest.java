package com.dukeacademy.compiler;

import com.dukeacademy.compiler.environment.StandardCompilerEnvironment;
import com.dukeacademy.compiler.exceptions.CompilerCompileException;
import com.dukeacademy.compiler.exceptions.CompilerEnvironmentException;
import com.dukeacademy.model.UserProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    @TempDir
    Path tempFolder;

    Path environmentPath;
    Compiler compiler;

    UserProgram validProgram;
    UserProgram invalidProgram;

    @BeforeEach
    void initializeTest() throws CompilerEnvironmentException {
        StandardCompilerEnvironment environment = new StandardCompilerEnvironment();
        environmentPath = tempFolder.resolve("compiler");
        environment.initialize(environmentPath.toUri().getPath());

        compiler = new Compiler(environment);

        validProgram = new UserProgram("ValidTest", "public class ValidTest {\n"
                + "\tpublic static void main(String args[]) {"
                + "\t\tSystem.out.println(\"Hello world\");"
                + "\t}"
                + "\n}");

        invalidProgram = new UserProgram("InvalidTest", "FooBar");
    }

    @Test
    void compilerCreation() {
        assertTrue(environmentPath.toFile().exists());
    }

    @Test
    void close() throws CompilerCompileException {
        compiler.compileProgram(validProgram);
        compiler.close();

        assertFalse(environmentPath.toFile().exists());
    }

    @Test
    void compileProgram() throws CompilerCompileException {
        compiler.compileProgram(validProgram);

        Path validJavaFilePath = environmentPath.resolve(validProgram.getClassName() + ".java");
        Path validClassFilePath = environmentPath.resolve(validProgram.getClassName() + ".class");

        assertTrue(validJavaFilePath.toFile().exists());
        assertTrue(validClassFilePath.toFile().exists());

        compiler.compileProgram(invalidProgram);

        Path invalidJavaFilePath = environmentPath.resolve(invalidProgram.getClassName() + ".java");
        Path invalidClassFilePath = environmentPath.resolve(invalidProgram.getClassName() + ".class");

        assertFalse(invalidClassFilePath.toFile().exists());
        assertFalse(invalidClassFilePath.toFile().exists());

        compiler.close();
    }
}