package com.dukeacademy.solution;

import com.dukeacademy.solution.environment.StandardCompilerEnvironment;
import com.dukeacademy.solution.exceptions.CompilerException;
import com.dukeacademy.solution.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solution.exceptions.CompilerFileContentException;
import com.dukeacademy.model.solution.UserProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    @TempDir
    Path tempFolder;

    private Path environmentPath;
    private Compiler compiler;

    private UserProgram validProgram = new UserProgram("ValidTest", "public class ValidTest {\n"
            + "\tpublic static void main(String args[]) {"
            + "\t\tSystem.out.println(\"Hello world\");"
            + "\t}"
            + "\n}");
    ;
    private UserProgram validProgram1 = new UserProgram("ValidTest1", "package foo.bar;\n"
            + "public class ValidTest1 {\n"
            + "\tpublic static void main(String args[]) {"
            + "\t\tSystem.out.println(\"Hello world\");"
            + "\t}"
            + "\n}");

    private UserProgram invalidProgram = new UserProgram("InvalidTest", "FooBar");

    @BeforeEach
    void initializeTest() throws CompilerEnvironmentException {
        environmentPath = tempFolder.resolve("compiler");
        TestEnvironment environment = new TestEnvironment(environmentPath);
        compiler = new Compiler(environment);
    }

    @Test
    void compilerCreation() {
        assertTrue(environmentPath.toFile().exists());
    }

    @Test
    void close() throws CompilerException, CompilerFileContentException {
        compiler.compileProgram(validProgram);
        compiler.close();

        assertFalse(environmentPath.toFile().exists());
    }

    @Test
    void compileProgram() throws CompilerException, CompilerFileContentException {
        compiler.compileProgram(validProgram);

        Path validJavaFilePath = environmentPath.resolve(validProgram.getClassName() + ".java");
        Path validClassFilePath = environmentPath.resolve(validProgram.getClassName() + ".class");

        assertTrue(validJavaFilePath.toFile().exists());
        assertTrue(validClassFilePath.toFile().exists());

        compiler.compileProgram(validProgram1);
        Path validJavaFilePath1 = environmentPath.resolve("foo").resolve("bar").resolve("ValidTest1" + ".java");
        Path validClassFilePath1 = environmentPath.resolve("foo").resolve("bar").resolve("ValidTest1" + ".class");

        assertTrue(validJavaFilePath1.toFile().exists());
        assertTrue(validClassFilePath1.toFile().exists());

        assertThrows(CompilerFileContentException.class, () -> compiler.compileProgram(invalidProgram));

        Path invalidJavaFilePath = environmentPath.resolve(invalidProgram.getClassName() + ".java");
        Path invalidClassFilePath = environmentPath.resolve(invalidProgram.getClassName() + ".class");

        assertFalse(invalidJavaFilePath.toFile().exists());
        assertFalse(invalidClassFilePath.toFile().exists());

        compiler.close();
    }
}