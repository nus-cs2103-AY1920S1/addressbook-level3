package com.dukeacademy.solution.environment;

import com.dukeacademy.solution.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solution.exceptions.CompilerFileCreationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StandardCompilerEnvironmentTest {
    StandardCompilerEnvironment compilerEnvironment;
    Path environmentPath;

    @TempDir
    static Path temporaryFolder;

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

        compilerEnvironment.createJavaFile(fileName, content);

        Path javaFilePath = environmentPath.resolve(fileName +  ".java");

        File javaFile = javaFilePath.toFile();
        assertTrue(javaFile.exists());


        Optional<String> fileContent = Files.lines(javaFilePath).reduce((x, y) -> x + "\n" + y);
        assertTrue(fileContent.isPresent());
        assertEquals(content, fileContent.get());
    }

    @Test
    void getJavaFile() throws CompilerFileCreationException, IOException {
        String fileName = "Test1";
        String content = "public class Test1 {\n}";

        compilerEnvironment.createJavaFile(fileName, content);

        File javaFile = compilerEnvironment.getJavaFile(fileName);
        assertTrue(javaFile.exists());
        assertEquals(fileName + ".java", javaFile.getName());

        Path filePath = environmentPath.resolve(fileName + ".java");

        Optional<String> fileContent = Files.lines(filePath).reduce((x, y) -> x + "\n" + y);
        assertTrue(fileContent.isPresent());
        assertEquals(content, fileContent.get());
    }

    @Test
    void close() throws CompilerFileCreationException {
        String fileName = "Test2";
        String content = "public class Test2 {\n}";
        compilerEnvironment.createJavaFile(fileName, content);

        compilerEnvironment.close();

        assertFalse(environmentPath.toFile().exists());
    }

    @Test
    void clearEnvironment() throws CompilerEnvironmentException, CompilerFileCreationException, IOException {
        String fileName = "Test2";
        String content = "public class Test2 {\n}";
        compilerEnvironment.createJavaFile(fileName, content);

        compilerEnvironment.clearEnvironment();

        long remainingFiles = Files.walk(environmentPath)
                .filter(path -> !path.equals(path))
                .count();

        assertEquals(0, remainingFiles);
    }
}