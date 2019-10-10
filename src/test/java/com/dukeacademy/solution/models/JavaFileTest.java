package com.dukeacademy.solution.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JavaFileTest {
    @TempDir
    Path tempFolder;

    @Test
    void testFileExists() throws IOException {
        String basePath = tempFolder.toUri().getPath();

        tempFolder.resolve("Foo.java").toFile().createNewFile();
        JavaFile file = new JavaFile("Foo", basePath);

        assertTrue(file.getFile().exists());
        assertEquals("Foo", file.getCanonicalName());
        assertEquals(basePath, file.getClassPath());

        tempFolder.resolve("nested").toFile().mkdir();
        tempFolder.resolve("nested").resolve("Bar.java").toFile().createNewFile();
        JavaFile file1 = new JavaFile("nested.Bar", basePath);

        assertTrue(file1.getFile().exists());
        assertEquals("nested.Bar", file1.getCanonicalName());
        assertEquals(basePath, file1.getClassPath());
    }

    @Test
    void testFileDoesNotExist() {
        String basePath = tempFolder.toUri().getPath();

        assertThrows(FileNotFoundException.class, () -> new ClassFile("Foobar", basePath));
    }
}