package com.dukeacademy.compiler.environment;

import com.dukeacademy.compiler.exceptions.FileCreationException;
import com.dukeacademy.compiler.exceptions.FileDirectoryCreationException;
import com.dukeacademy.compiler.exceptions.FileDirectoryDeletionException;
import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileNotFoundException;
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

        String fileAbsolutePath = environmentPath.resolve(fileName).toUri().getPath();
        File file = new File(fileAbsolutePath);

        assertTrue(file.exists());

        Optional<String> fileContent = Files.lines(Path.of(fileAbsolutePath))
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

        String fileAbsolutePath = environmentPath.resolve(fileName).toUri().getPath();

        Optional<String> fileContent = Files.lines(Path.of(fileAbsolutePath)).reduce((x, y) -> x + "\n" + y);
        assertTrue(fileContent.isPresent());
        assertEquals(content, fileContent.get());
    }

    @Test
    void clearDirectory() throws FileDirectoryDeletionException, FileCreationException {
        String fileName = "test2.txt";
        String content = "hello12345";
        fileManager.createFile(fileName, content);

        fileManager.clearDirectory();

        assertFalse(new File(environmentPath.toUri().getPath()).exists());
    }
}