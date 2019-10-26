package com.dukeacademy.testexecutor.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.testexecutor.environment.exceptions.JavaFileCreationException;
import com.dukeacademy.testexecutor.exceptions.IncorrectCanonicalNameException;

class JavaFileTest {
    @TempDir
    public Path tempFolder;

    private String fooProgram = "public class Foo {}";
    private String insideFooProgram = "package packaged.inside;\n"
            + "public class Foo {}";
    /**
     * Instance creation should succeed if the class file actually exists.
     * @throws IOException
     */
    @Test
    public void testJavaFileConstructorFileExists() throws IOException, JavaFileCreationException,
            IncorrectCanonicalNameException {
        String basePath = tempFolder.toString();

        Path filePath = tempFolder.resolve("Foo.java");
        filePath.toFile().createNewFile();
        Files.writeString(filePath, fooProgram);
        new JavaFile("Foo", basePath);

        // Tests when the class is packaged
        Path packageFolders = tempFolder.resolve("packaged").resolve("inside");
        packageFolders.toFile().mkdirs();
        Path packagedFilePath = packageFolders.resolve("Foo.java");
        packagedFilePath.toFile().createNewFile();
        Files.writeString(packagedFilePath, insideFooProgram);
        new JavaFile("packaged.inside.Foo", basePath);
    }

    @Test
    public void testJavaFileConstructorAndGetters() throws IOException, JavaFileCreationException,
            IncorrectCanonicalNameException {
        String basePath = tempFolder.toString();

        Path filePath = tempFolder.resolve("Foo.java");
        filePath.toFile().createNewFile();
        Files.writeString(filePath, fooProgram);
        JavaFile fooFile = new JavaFile("Foo", basePath);

        assertEquals("Foo", fooFile.getCanonicalName());
        assertEquals(basePath, fooFile.getClassPath());

        // Tests when the class is packaged
        Path packageFolders = tempFolder.resolve("packaged").resolve("inside");
        packageFolders.toFile().mkdirs();
        Path packagedFilePath = packageFolders.resolve("Foo.java");
        packagedFilePath.toFile().createNewFile();
        Files.writeString(packagedFilePath, insideFooProgram);
        JavaFile packagedFooFile = new JavaFile("packaged.inside.Foo", basePath);
        assertEquals("packaged.inside.Foo", packagedFooFile.getCanonicalName());
        assertEquals(basePath, packagedFooFile.getClassPath());
    }

    @Test
    public void testJavaFileConstructorMismatchedCanonicalName() {
        // TODO
    }

    /**
     * Instance creation should fail if the class does not exist. A FileNotFoundException should be thrown.
     */
    @Test
    public void testJavaFileConstructorFileDoesNotExists() throws IOException {
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
    public void testJavaFileConstructorNullArguments() {
        String basePath = tempFolder.toString();
        assertThrows(NullPointerException.class, () -> new JavaFile(null, basePath));
        assertThrows(NullPointerException.class, () -> new JavaFile("Foo", null));
    }

    @Test
    void testGetAbsolutePath() throws IOException, JavaFileCreationException, IncorrectCanonicalNameException {
        String basePath = tempFolder.toString();

        Path fooPath = tempFolder.resolve("Foo.java");
        fooPath.toFile().createNewFile();
        Files.writeString(fooPath, fooProgram);

        String fooExpectedAbsolutePath = fooPath.toString();
        String fooActualAbsolutePath = new JavaFile("Foo", basePath).getAbsolutePath();
        assertEquals(fooExpectedAbsolutePath, fooActualAbsolutePath);

        // Check absolute path for packaged classes
        Path fooPackageFolders = tempFolder.resolve("packaged").resolve("inside");
        fooPackageFolders.toFile().mkdirs();
        Path fooInsidePath = fooPackageFolders.resolve("Foo.java");
        fooInsidePath.toFile().createNewFile();
        Files.writeString(fooInsidePath, insideFooProgram);

        String fooInsideExpectedPath = fooInsidePath.toString();
        String fooInsideActualPath = new JavaFile("packaged.inside.Foo", basePath)
                .getAbsolutePath();
        assertEquals(fooInsideExpectedPath, fooInsideActualPath);
    }

    @Test
    void testGetFile() throws IOException, JavaFileCreationException, IncorrectCanonicalNameException {
        String basePath = tempFolder.toString();

        Path fooPath = tempFolder.resolve("Foo.java");
        fooPath.toFile().createNewFile();
        Files.writeString(fooPath, fooProgram);

        File expectedFooFile = fooPath.toFile();
        File actualFooFile = new JavaFile("Foo", basePath).getFile();
        assertEquals(expectedFooFile, actualFooFile);

        // Check File for packaged classes.
        Path fooPackageFolders = tempFolder.resolve("packaged").resolve("inside");
        fooPackageFolders.toFile().mkdirs();
        Path insideFooPath = fooPackageFolders.resolve("Foo.java");
        insideFooPath.toFile().createNewFile();
        Files.writeString(insideFooPath, insideFooProgram);

        File expectedInsideFooFile = insideFooPath.toFile();
        File actualInsideFooFile = new JavaFile("packaged.inside.Foo", basePath).getFile();
        assertEquals(expectedInsideFooFile, actualInsideFooFile);
    }

    @Test
    void testEquals() throws IOException, JavaFileCreationException, IncorrectCanonicalNameException {
        String basePath = tempFolder.toString();

        Path fooPath = tempFolder.resolve("Foo.java");
        fooPath.toFile().createNewFile();
        Files.writeString(fooPath, fooProgram);

        Path barPath = tempFolder.resolve("Bar.java");
        barPath.toFile().createNewFile();
        Files.writeString(barPath, "public class Bar {}");

        JavaFile fooFile1 = new JavaFile("Foo", basePath);
        JavaFile fooFile2 = new JavaFile("Foo", basePath);
        assertEquals(fooFile1, fooFile2);

        JavaFile barFile = new JavaFile("Bar", basePath);
        assertNotEquals(fooFile1, barFile);

        // Tests for classes with the same name but different class paths.
        Path packageFolders = tempFolder.resolve("packaged").resolve("inside");
        packageFolders.toFile().mkdirs();
        Path packagedFooPath = packageFolders.resolve("Foo.java");
        packagedFooPath.toFile().createNewFile();
        Files.writeString(packagedFooPath, insideFooProgram);

        JavaFile packagedFooFile = new JavaFile("packaged.inside.Foo", basePath);
        assertNotEquals(fooFile1, packagedFooFile);
    }
}
