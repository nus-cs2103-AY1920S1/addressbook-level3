package com.dukeacademy.compiler.environment;

import com.dukeacademy.compiler.exceptions.CompilerEnvironmentException;
import com.dukeacademy.compiler.exceptions.FileCreationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
    StandardCompilerEnvironment compilerEnvironment;
    String environmentPath;

    @TempDir
    Path temporaryFolder;

    @BeforeEach
    void initializeTest() throws CompilerEnvironmentException {
        compilerEnvironment = new StandardCompilerEnvironment();
        environmentPath = temporaryFolder.resolve("compiler").toUri().getPath();
        compilerEnvironment.initiate(environmentPath);
    }

    @Test
    void initiate() {
        assertTrue(compilerEnvironment.isInitialized());
        assertTrue(new File(environmentPath).exists());
        assertEquals(compilerEnvironment.getEnvironmentPath(), environmentPath);
    }

    @Test
    void createJavaFile() throws CompilerEnvironmentException, FileCreationException, IOException {
        String fileName = "Test";
        String content = "public class Test {\n}";

        compilerEnvironment.createJavaFile(fileName, content);

        String javaFilePath = environmentPath + File.separator + fileName +  ".java";

        File javaFile = new File(javaFilePath);
        assertTrue(javaFile.exists());


        Optional<String> fileContent = Files.lines(Path.of(javaFilePath)).reduce((x, y) -> x + "\n" + y);
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

        String fileAbsolutePath = environmentPath + File.separator + fileName + ".java";

        Optional<String> fileContent = Files.lines(Path.of(fileAbsolutePath)).reduce((x, y) -> x + "\n" + y);
        assertTrue(fileContent.isPresent());
        assertEquals(content, fileContent.get());
    }

    @Test
    void close() throws CompilerEnvironmentException, FileCreationException {
        String fileName = "Test2";
        String content = "public class Test2 {\n}";
        compilerEnvironment.createJavaFile(fileName, content);

        compilerEnvironment.close();

        assertFalse(new File(environmentPath).exists());
    }
}