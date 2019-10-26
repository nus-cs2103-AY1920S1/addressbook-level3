package com.dukeacademy.testexecutor.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ClassFileTest {
    @TempDir
    public Path tempFolder;

    /**
     * Instance creation should succeed if the class file actually exists.
     * @throws IOException
     */
    @Test
    public void testClassFileConstructorFileExists() throws IOException {
        String basePath = tempFolder.toString();

        tempFolder.resolve("Foo.class").toFile().createNewFile();
        new ClassFile("Foo", basePath);

        // Tests when the class is packaged
        Path packageFolders = tempFolder.resolve("packaged").resolve("inside");
        packageFolders.toFile().mkdirs();
        packageFolders.resolve("Foo.class").toFile().createNewFile();
        new ClassFile("packaged.inside.Foo", basePath);
    }

    @Test
    public void testJavaFileConstructorAndGetters() throws IOException {
        String basePath = tempFolder.toString();

        tempFolder.resolve("Foo.class").toFile().createNewFile();
        ClassFile fooFile = new ClassFile("Foo", basePath);

        assertEquals("Foo", fooFile.getCanonicalName());
        assertEquals(basePath, fooFile.getClassPath());

        // Tests when the class is packaged
        Path packageFolders = tempFolder.resolve("packaged").resolve("inside");
        packageFolders.toFile().mkdirs();
        packageFolders.resolve("Foo.class").toFile().createNewFile();
        ClassFile packagedFooFile = new ClassFile("packaged.inside.Foo", basePath);
        assertEquals("packaged.inside.Foo", packagedFooFile.getCanonicalName());
        assertEquals(basePath, packagedFooFile.getClassPath());
    }

    /**
     * Instance creation should fail if the class does not exist. A FileNotFoundException should be thrown.
     */
    @Test
    public void testClassFileConstructorFileDoesNotExists() throws IOException {
        String basePath = tempFolder.toString();

        assertThrows(FileNotFoundException.class, () -> new ClassFile("Foo", basePath));
        assertThrows(FileNotFoundException.class, () -> new ClassFile("", basePath));
        assertThrows(FileNotFoundException.class, () -> new ClassFile("Foo", ""));

        // Tests when another file exists in the folder
        tempFolder.resolve("Bar.class").toFile().createNewFile();
        assertThrows(FileNotFoundException.class, () -> new ClassFile("Foo", basePath));

        // Tests when the classpath is wrong
        assertThrows(FileNotFoundException.class, () -> new ClassFile("Bar", "/duke/academy"));

        // Tests when package is not reflected in canonical name
        Path packageFolders = tempFolder.resolve("packaged").resolve("inside");
        packageFolders.toFile().mkdirs();
        packageFolders.resolve("Foo.class").toFile().createNewFile();
        assertThrows(FileNotFoundException.class, () -> new ClassFile("Foo", basePath));
    }

    @Test
    public void testClassFileConstructorNullArguments() {
        String basePath = tempFolder.toString();
        assertThrows(NullPointerException.class, () -> new ClassFile(null, basePath));
        assertThrows(NullPointerException.class, () -> new ClassFile("Foo", null));
    }

    @Test
    void testGetAbsolutePath() throws IOException {
        String basePath = tempFolder.toString();

        Path fooPath = tempFolder.resolve("Foo.class");
        fooPath.toFile().createNewFile();
        String fooExpectedAbsolutePath = fooPath.toString();
        String fooActualAbsolutePath = new ClassFile("Foo", basePath).getAbsolutePath();
        assertEquals(fooExpectedAbsolutePath, fooActualAbsolutePath);

        // Check absolute path for packaged classes
        Path barPackageFolders = tempFolder.resolve("packaged").resolve("nested");
        barPackageFolders.toFile().mkdirs();
        Path barPath = barPackageFolders.resolve("Bar.class");
        barPath.toFile().createNewFile();
        String barExpectedAbsolutePath = barPath.toString();
        String barActualAbsolutePath = new ClassFile("Bar", barPackageFolders.toString())
                .getAbsolutePath();
        assertEquals(barExpectedAbsolutePath, barActualAbsolutePath);
    }

    @Test
    void testGetFile() throws IOException {
        String basePath = tempFolder.toString();

        Path fooPath = tempFolder.resolve("Foo.class");
        fooPath.toFile().createNewFile();
        File expectedFooFile = fooPath.toFile();
        File actualFooFile = new ClassFile("Foo", basePath).getFile();
        assertEquals(expectedFooFile, actualFooFile);

        // Check File for packaged classes.
        Path barPackageFolders = tempFolder.resolve("packaged").resolve("nested");
        barPackageFolders.toFile().mkdirs();
        Path barPath = barPackageFolders.resolve("Bar.class");
        barPath.toFile().createNewFile();
        File expectedBarFile = barPath.toFile();
        File actualBarFile = new ClassFile("Bar", barPackageFolders.toString()).getFile();
        assertEquals(expectedBarFile, actualBarFile);
    }

    @Test
    void testEquals() throws IOException {
        String basePath = tempFolder.toString();

        Path fooPath = tempFolder.resolve("Foo.class");
        fooPath.toFile().createNewFile();
        Path barPath = tempFolder.resolve("Bar.class");
        barPath.toFile().createNewFile();

        ClassFile fooFile1 = new ClassFile("Foo", basePath);
        ClassFile fooFile2 = new ClassFile("Foo", basePath);
        assertEquals(fooFile1, fooFile2);

        ClassFile barFile = new ClassFile("Bar", basePath);
        assertNotEquals(fooFile1, barFile);

        // Tests for classes with the same name but different class paths.
        Path packageFolders = tempFolder.resolve("packaged").resolve("nested");
        packageFolders.toFile().mkdirs();
        Path packagedFooPath = packageFolders.resolve("Foo.class");
        packagedFooPath.toFile().createNewFile();

        ClassFile packagedFooFile = new ClassFile("Foo", packageFolders.toString());
        assertNotEquals(fooFile1, packagedFooFile);
    }
}
