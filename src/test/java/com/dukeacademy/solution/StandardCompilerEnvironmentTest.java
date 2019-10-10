package com.dukeacademy.solution;

import com.dukeacademy.model.solution.UserProgram;
import com.dukeacademy.solution.StandardCompilerEnvironment;
import com.dukeacademy.solution.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solution.exceptions.CompilerFileCreationException;
import com.dukeacademy.solution.models.JavaFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StandardCompilerEnvironmentTest {
    private StandardCompilerEnvironment compilerEnvironment;
    private Path environmentPath;

    @TempDir
    Path temporaryFolder;

    @BeforeEach
    void initializeTest() throws CompilerEnvironmentException {
        environmentPath = temporaryFolder.resolve("compiler");
        compilerEnvironment = new StandardCompilerEnvironment(environmentPath.toUri().getPath());
    }

    @AfterEach
    void closeTest() {
        compilerEnvironment.close();
    }

    @Test
    void createJavaFile() throws CompilerFileCreationException, IOException {
        String fileName = "Test";
        String content = "public class Test {\n}";
        UserProgram program = new UserProgram(fileName, content);

        JavaFile createdJavaFile = compilerEnvironment.createJavaFile(program);
        assertEquals("Test", createdJavaFile.getCanonicalName());
        assertEquals(environmentPath.toUri().getPath(), createdJavaFile.getClassPath());

        Path javaFilePath = environmentPath.resolve(fileName +  ".java");

        File javaFile = javaFilePath.toFile();
        assertTrue(javaFile.exists());


        Optional<String> fileContent = Files.lines(javaFilePath).reduce((x, y) -> x + "\n" + y);
        assertTrue(fileContent.isPresent());
        assertEquals(content, fileContent.get());

        String fileName1 = "Test";
        String content1 = "package foo.bar;\n"
                + "public class Test {\n}";

        UserProgram program1 = new UserProgram(fileName1, content1);

        JavaFile createdJavaFile1 = compilerEnvironment.createJavaFile(program1);
        assertEquals("foo.bar.Test", createdJavaFile1.getCanonicalName());
        assertEquals(environmentPath.toUri().getPath(), createdJavaFile1.getClassPath());

        Path javaFilePath1 = environmentPath.resolve("foo").resolve("bar").resolve("Test.java");

        File javaFile1 = javaFilePath1.toFile();
        assertTrue(javaFile1.exists());


        Optional<String> fileContent1 = Files.lines(javaFilePath1).reduce((x, y) -> x + "\n" + y);
        assertTrue(fileContent1.isPresent());
        assertEquals(content1, fileContent1.get());
    }

    @Test
    void getJavaFile() throws CompilerFileCreationException, IOException {
        String fileName = "Test1";
        String content = "public class Test1 {\n}";

        UserProgram program = new UserProgram(fileName, content);

        compilerEnvironment.createJavaFile(program);

        JavaFile javaFile = compilerEnvironment.getJavaFile(fileName);
        assertTrue(javaFile.getFile().exists());
        assertEquals(fileName + ".java", javaFile.getFile().getName());

        Path filePath = environmentPath.resolve(fileName + ".java");

        Optional<String> fileContent = Files.lines(filePath).reduce((x, y) -> x + "\n" + y);
        assertTrue(fileContent.isPresent());
        assertEquals(content, fileContent.get());

        assertThrows(FileNotFoundException.class,
                () -> compilerEnvironment.getJavaFile("this.should.throw.an.error"));
    }

    @Test
    void close() throws CompilerFileCreationException {
        String fileName = "Test2";
        String content = "public class Test2 {\n}";
        UserProgram program = new UserProgram(fileName, content);

        compilerEnvironment.createJavaFile(program);

        compilerEnvironment.close();

        assertFalse(environmentPath.toFile().exists());
    }

    @Test
    void clearEnvironment() throws CompilerEnvironmentException, CompilerFileCreationException, IOException {
        String fileName = "Test2";
        String content = "public class Test2 {\n}";
        UserProgram program = new UserProgram(fileName, content);

        compilerEnvironment.createJavaFile(program);

        compilerEnvironment.clearEnvironment();

        long remainingFiles = Files.walk(environmentPath)
                .filter(path -> !path.equals(path))
                .count();

        assertEquals(0, remainingFiles);
    }
}