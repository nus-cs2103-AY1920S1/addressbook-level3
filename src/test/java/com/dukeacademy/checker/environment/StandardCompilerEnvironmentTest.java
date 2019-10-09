package com.dukeacademy.checker.environment;

import com.dukeacademy.checker.exceptions.CompilerEnvironmentException;
import com.dukeacademy.checker.exceptions.FileCreationException;
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
    Path temporaryFolder;

    @BeforeEach
    void initializeTest() throws CompilerEnvironmentException {
        compilerEnvironment = new StandardCompilerEnvironment();
        environmentPath = temporaryFolder.resolve("compiler");
        compilerEnvironment.initialize(environmentPath.toUri().getPath());
    }

    @Test
    void initiate() {
        assertTrue(compilerEnvironment.isInitialized());
        assertTrue(environmentPath.toFile().exists());
        assertEquals(environmentPath + File.separator, compilerEnvironment.getEnvironmentPath());
    }

    @Test
    void createJavaFile() throws CompilerEnvironmentException, FileCreationException, IOException {
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
    void getJavaFile() throws CompilerEnvironmentException, FileCreationException, IOException {
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
    void close() throws CompilerEnvironmentException, FileCreationException {
        String fileName = "Test2";
        String content = "public class Test2 {\n}";
        compilerEnvironment.createJavaFile(fileName, content);

        compilerEnvironment.close();

        assertFalse(environmentPath.toFile().exists());
    }

    @Test
    void clearEnvironment() throws CompilerEnvironmentException, FileCreationException, IOException {
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