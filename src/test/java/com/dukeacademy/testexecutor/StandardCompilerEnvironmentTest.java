package com.dukeacademy.testexecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.testexecutor.environment.StandardCompilerEnvironment;
import com.dukeacademy.testexecutor.environment.exceptions.ClearEnvironmentException;
import com.dukeacademy.testexecutor.environment.exceptions.ClosedEnvironmentException;
import com.dukeacademy.testexecutor.environment.exceptions.CreateEnvironmentException;
import com.dukeacademy.testexecutor.environment.exceptions.JavaFileCreationException;
import com.dukeacademy.testexecutor.exceptions.IncorrectCanonicalNameException;
import com.dukeacademy.testexecutor.models.JavaFile;

class StandardCompilerEnvironmentTest {
    @TempDir
    public Path tempFolder;

    /**
     * A folder should be created at the location path of the environment
     */
    @Test
    public void testConstructor() throws CreateEnvironmentException {
        Path environmentPath = tempFolder.resolve("constructor_test");
        new StandardCompilerEnvironment(environmentPath);
        assertTrue(environmentPath.toFile().exists());

        // Tests for nested folders
        Path nestedEnvironmentPath = tempFolder.resolve("nested").resolve("constructor_test");
        new StandardCompilerEnvironment(nestedEnvironmentPath);
        assertTrue(nestedEnvironmentPath.toFile().exists());
    }

    /**
     * User programs should be successfully written to created Java files. Returned JavaFile instance should also have
     * the correct canonical name and classpath.
     */
    @Test
    public void testCreateJavaFile() throws JavaFileCreationException, IOException, CreateEnvironmentException,
            IncorrectCanonicalNameException {
        Path environmentPath = tempFolder.resolve("createJavaFile_test");
        StandardCompilerEnvironment environment = new StandardCompilerEnvironment(environmentPath);

        String validFileName = "Test";
        String validContent = "public class Test { }";

        JavaFile validJavaFile = environment.createJavaFile(new UserProgram(validFileName, validContent));
        JavaFile expectedJavaFile = new JavaFile(validFileName, environmentPath.toString());
        assertEquals(expectedJavaFile, validJavaFile);

        // JavaFile class already ensures that the file exists
        String validJavaFileContent = Files.readString(validJavaFile.getFile().toPath());
        assertEquals(validContent, validJavaFileContent);

        // Tests for packaged files
        String validPackagedFileName = "packaged.Test";
        String validPackagedContent = "package packaged;\n\npublic class Test { }";

        JavaFile validPackagedJavaFile = environment.createJavaFile(new UserProgram(validPackagedFileName,
                validPackagedContent));
        // Package name must be added manually to form canonical name. Note that UserProgram takes the class name
        // while JavaFile takes the canonical name as arguments for their constructors
        JavaFile expectedPackagedJavaFile = new JavaFile(validPackagedFileName,
                environmentPath.toString());
        assertEquals(expectedPackagedJavaFile, validPackagedJavaFile);

        String validPackagedFileContent = Files.readString(validPackagedJavaFile.getFile().toPath());
        assertEquals(validPackagedContent, validPackagedFileContent);
    }

    /**
     * Created Java files should be returned with the correct attribute values.
     */
    @Test
    public void testGetJavaFile() throws JavaFileCreationException, IOException, CreateEnvironmentException,
            IncorrectCanonicalNameException {
        Path environmentPath = tempFolder.resolve("getJavaFile_test");
        StandardCompilerEnvironment environment = new StandardCompilerEnvironment(environmentPath);

        String validFileName = "Test";
        String validContent = "public class Test { }";

        environment.createJavaFile(new UserProgram(validFileName, validContent));
        JavaFile expectedJavaFile = new JavaFile(validFileName, environmentPath.toString());
        assertEquals(expectedJavaFile, environment.getJavaFile("Test"));

        // Tests for packaged files
        String validPackagedFileName = "packaged.Test";
        String validPackagedContent = "package packaged;\n\npublic class Test { }";

        environment.createJavaFile(new UserProgram(validPackagedFileName, validPackagedContent));
        JavaFile expectedPackagedJavaFile = new JavaFile(validPackagedFileName, environmentPath.toString());
        assertEquals(expectedPackagedJavaFile, environment.getJavaFile(validPackagedFileName));
    }

    /**
     * After closing, the folder in which the environment was created should be deleted.
     */
    @Test
    public void testClose() throws CreateEnvironmentException {
        Path environmentPath = tempFolder.resolve("close_test");
        StandardCompilerEnvironment environment = new StandardCompilerEnvironment(environmentPath);

        assertTrue(environmentPath.toFile().exists());
        environment.close();
        assertFalse(environmentPath.toFile().exists());

        assertThrows(ClosedEnvironmentException.class, () -> environment
                .createJavaFile(new UserProgram("", "")));
        assertThrows(ClosedEnvironmentException.class, () -> environment.getJavaFile(""));
        assertThrows(ClosedEnvironmentException.class, environment::clearEnvironment);
        assertThrows(ClosedEnvironmentException.class, environment::close);
    }

    /**
     * After clearing, all files previously found in the environment should be deleted.
     */
    @Test
    public void testClearEnvironment() throws IOException, ClearEnvironmentException, CreateEnvironmentException {
        Path environmentPath = tempFolder.resolve("clear_test");
        StandardCompilerEnvironment environment = new StandardCompilerEnvironment(environmentPath);

        environmentPath.resolve("testfile1").toFile().createNewFile();
        environmentPath.resolve("testfile2.java").toFile().createNewFile();
        environmentPath.resolve("testfile3.class").toFile().createNewFile();

        environment.clearEnvironment();
        assertTrue(environmentPath.toFile().listFiles().length == 0);
    }
}
