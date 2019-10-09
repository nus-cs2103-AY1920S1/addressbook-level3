package com.dukeacademy.checker.environment;

import com.dukeacademy.checker.exceptions.FileCreationException;
import com.dukeacademy.checker.exceptions.FileDeletionException;
import com.dukeacademy.checker.exceptions.FileDirectoryCreationException;
import com.dukeacademy.checker.exceptions.FileDirectoryDeletionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StandardCompilerFileManagerTest {
    StandardCompilerFileManager fileManager;
    Path environmentPath;

    @TempDir
    Path temporaryFolder;

    @BeforeEach
    void initializeTest() throws FileDirectoryCreationException {
        environmentPath = temporaryFolder.resolve("compiler");
        fileManager = new StandardCompilerFileManager(environmentPath.toUri().getPath());
    }

    @Test
    void createFile() throws FileCreationException, IOException {
        String fileName = "test.txt";
        String content = "ABCDE12345\n qwerty";

        File createdFile = fileManager.createFile(fileName, content);
        assertTrue(createdFile.exists());

        Path fileAbsolutePath =environmentPath.resolve(fileName);

        File file = environmentPath.resolve(fileName).toFile();

        assertTrue(file.exists());

        Optional<String> fileContent = Files.lines(fileAbsolutePath)
                .reduce((x, y) -> x + "\n" + y);
        assertTrue(fileContent.isPresent());
        assertEquals(content, fileContent.get());
    }

    @Test
    void getFile() throws FileCreationException, IOException {
        String fileName = "test1.txt";
        String content = "12345ABCDE \n trewq";

        fileManager.createFile(fileName, content);
        File file = fileManager.getFile(fileName);

        assertTrue(file.exists());
        assertEquals(file.getName(), fileName);

        Path fileAbsolutePath = environmentPath.resolve(fileName);

        Optional<String> fileContent = Files.lines(fileAbsolutePath).reduce((x, y) -> x + "\n" + y);
        assertTrue(fileContent.isPresent());
        assertEquals(content, fileContent.get());
    }

    @Test
    void deleteDirectory() throws FileDirectoryDeletionException, FileCreationException {
        String fileName = "test2.txt";
        String content = "hello12345";
        fileManager.createFile(fileName, content);

        fileManager.deleteDirectory();

        assertFalse(new File(environmentPath.toUri().getPath()).exists());
    }

    @Test
    void clearDirectory() throws FileCreationException, FileDeletionException, IOException {
        String fileName = "test2.txt";
        String content = "hello12345";
        fileManager.createFile(fileName, content);

        fileManager.clearDirectory();

        long remainingFiles = Files.walk(environmentPath)
                .filter(path -> !path.equals(path))
                .count();

        assertEquals(0, remainingFiles);
    }
}