package com.dukeacademy.checker;

import com.dukeacademy.checker.compiler.Compiler;
import com.dukeacademy.checker.environment.StandardCompilerEnvironment;
import com.dukeacademy.checker.exceptions.CompilerException;
import com.dukeacademy.checker.exceptions.CompilerEnvironmentException;
import com.dukeacademy.checker.exceptions.UserProgramException;
import com.dukeacademy.model.UserProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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
    void close() throws CompilerException, UserProgramException {
        compiler.compileProgram(validProgram);
        compiler.close();

        assertFalse(environmentPath.toFile().exists());
    }

    @Test
    void compileProgram() throws CompilerException, UserProgramException {
        compiler.compileProgram(validProgram);

        Path validJavaFilePath = environmentPath.resolve(validProgram.getClassName() + ".java");
        Path validClassFilePath = environmentPath.resolve(validProgram.getClassName() + ".class");

        assertTrue(validJavaFilePath.toFile().exists());
        assertTrue(validClassFilePath.toFile().exists());

        assertThrows(UserProgramException.class, () -> compiler.compileProgram(invalidProgram));

        Path invalidJavaFilePath = environmentPath.resolve(invalidProgram.getClassName() + ".java");
        Path invalidClassFilePath = environmentPath.resolve(invalidProgram.getClassName() + ".class");

        assertFalse(invalidJavaFilePath.toFile().exists());
        assertFalse(invalidClassFilePath.toFile().exists());

        compiler.close();
    }
}