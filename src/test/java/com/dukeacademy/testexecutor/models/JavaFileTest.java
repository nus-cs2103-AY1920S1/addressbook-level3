package com.dukeacademy.testexecutor.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JavaFileTest {
    @TempDir
    public Path tempFolder;

    /**
     * Instance creation should succeed if the class file actually exists.
     * @throws IOException
     */
    @Test
    public void testJavaFileConstructor_FileExists() throws IOException {
        String basePath = tempFolder.toString();

        tempFolder.resolve("Foo.java").toFile().createNewFile();
        new JavaFile("Foo", basePath);

        // Tests when the class is packaged
        Path packageFolders = tempFolder.resolve("packaged").resolve("inside");
        packageFolders.toFile().mkdirs();
        packageFolders.resolve("Foo.java").toFile().createNewFile();
        new JavaFile("packaged.inside.Foo", basePath);
    }

    @Test
    public void testJavaFileConstructorAndGetters() throws IOException {
        String basePath = tempFolder.toString();

        tempFolder.resolve("Foo.java").toFile().createNewFile();
        JavaFile fooFile = new JavaFile("Foo", basePath);

        assertEquals("Foo", fooFile.getCanonicalName());
        assertEquals(basePath, fooFile.getClassPath());

        // Tests when the class is packaged
        Path packageFolders = tempFolder.resolve("packaged").resolve("inside");
        packageFolders.toFile().mkdirs();
        packageFolders.resolve("Foo.java").toFile().createNewFile();
        JavaFile packagedFooFile = new JavaFile("packaged.inside.Foo", basePath);
        assertEquals("packaged.inside.Foo", packagedFooFile.getCanonicalName());
        assertEquals(basePath, packagedFooFile.getClassPath());
    }

    /**
     * Instance creation should fail if the class does not exist. A FileNotFoundException should be thrown.
     */
    @Test
    public void testJavaFileConstructor_FileDoesNotExists() throws IOException {
        String basePath = tempFolder.toString();

        assertThrows(FileNotFoundException.class, () -> new JavaFile("Foo", basePath));
        assertThrows(FileNotFoundException.class, () -> new JavaFile("", basePath));
        assertThrows(FileNotFoundException.class, () -> new JavaFile("Foo", ""));

        // Tests when another file exists in the folder
        tempFolder.resolve("Bar.class").toFile().createNewFile();
        assertThrows(FileNotFoundException.class, () -> new JavaFile("Foo", basePath));

        // Tests when the classpath is wrong
        assertThrows(FileNotFoundException.class, () -> new JavaFile("Bar", "/duke/academy"));

        // Tests when package is not reflected in canonical name
        Path packageFolders = tempFolder.resolve("packaged").resolve("inside");
        packageFolders.toFile().mkdirs();
        packageFolders.resolve("Foo.java").toFile().createNewFile();
        assertThrows(FileNotFoundException.class, () -> new JavaFile("Foo", basePath));
    }

    @Test
    public void testJavaFileConstructor_NullArguments() {
        String basePath = tempFolder.toString();
        assertThrows(NullPointerException.class, () -> new JavaFile(null, basePath));
        assertThrows(NullPointerException.class, () -> new JavaFile("Foo", null));
    }

    @Test
    void testGetAbsolutePath() throws IOException {
        String basePath = tempFolder.toString();

        Path fooPath = tempFolder.resolve("Foo.java");
        fooPath.toFile().createNewFile();
        String fooExpectedAbsolutePath = fooPath.toString();
        String fooActualAbsolutePath = new JavaFile("Foo", basePath).getAbsolutePath();
        assertEquals(fooExpectedAbsolutePath, fooActualAbsolutePath);

        // Check absolute path for packaged classes
        Path barPackageFolders = tempFolder.resolve("packaged").resolve("nested");
        barPackageFolders.toFile().mkdirs();
        Path barPath = barPackageFolders.resolve("Bar.java");
        barPath.toFile().createNewFile();
        String barExpectedAbsolutePath = barPath.toString();
        String barActualAbsolutePath = new JavaFile("Bar", barPackageFolders.toString())
                .getAbsolutePath();
        assertEquals(barExpectedAbsolutePath, barActualAbsolutePath);
    }

    @Test
    void testGetFile() throws IOException {
        String basePath = tempFolder.toString();

        Path fooPath = tempFolder.resolve("Foo.java");
        fooPath.toFile().createNewFile();
        File expectedFooFile = fooPath.toFile();
        File actualFooFile = new JavaFile("Foo", basePath).getFile();
        assertEquals(expectedFooFile, actualFooFile);

        // Check File for packaged classes.
        Path barPackageFolders = tempFolder.resolve("packaged").resolve("nested");
        barPackageFolders.toFile().mkdirs();
        Path barPath = barPackageFolders.resolve("Bar.java");
        barPath.toFile().createNewFile();
        File expectedBarFile = barPath.toFile();
        File actualBarFile = new JavaFile("Bar", barPackageFolders.toString()).getFile();
        assertEquals(expectedBarFile, actualBarFile);
    }

    @Test
    void testEquals() throws IOException {
        String basePath = tempFolder.toString();

        Path fooPath = tempFolder.resolve("Foo.java");
        fooPath.toFile().createNewFile();
        Path barPath = tempFolder.resolve("Bar.java");
        barPath.toFile().createNewFile();

        JavaFile fooFile1 = new JavaFile("Foo", basePath);
        JavaFile fooFile2 = new JavaFile("Foo", basePath);
        assertEquals(fooFile1, fooFile2);

        JavaFile barFile = new JavaFile("Bar", basePath);
        assertNotEquals(fooFile1, barFile);

        // Tests for classes with the same name but different class paths.
        Path packageFolders = tempFolder.resolve("packaged").resolve("nested");
        packageFolders.toFile().mkdirs();
        Path packagedFooPath = packageFolders.resolve("Foo.java");
        packagedFooPath.toFile().createNewFile();

        JavaFile packagedFooFile = new JavaFile("Foo", packageFolders.toString());
        assertNotEquals(fooFile1, packagedFooFile);
    }
}
