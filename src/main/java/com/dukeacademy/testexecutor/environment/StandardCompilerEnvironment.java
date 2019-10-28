package com.dukeacademy.testexecutor.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.testexecutor.environment.exceptions.ClearEnvironmentException;
import com.dukeacademy.testexecutor.environment.exceptions.ClosedEnvironmentException;
import com.dukeacademy.testexecutor.environment.exceptions.CreateEnvironmentException;
import com.dukeacademy.testexecutor.environment.exceptions.JavaFileCreationException;
import com.dukeacademy.testexecutor.exceptions.IncorrectCanonicalNameException;
import com.dukeacademy.testexecutor.models.JavaFile;

/**
 * The standard implementation of the CompilerEnvironment interface that used by the application to create and manage
 * files required for testing the user's answers. The standard implementation uses Java's standard libraries for IO,
 * files and paths to generate and maintain these files.
 */
public class StandardCompilerEnvironment implements CompilerEnvironment {
    // Standard error messages
    private static final String messageUnableToCreateFolder = " Unable to create folder at : ";
    private static final String messageCreateJavaFileFailed = "Failed to create java file : ";
    private static final String messageWriteUserProgramFailed = "Write user program failed : ";
    private static final String messageJavaFileNotFound = "Matching Java file not found : ";
    private static final String messageClearEnvironmentFailed = "Unable to delete files and folders at : ";

    // Logger used to report behavior of the environment
    private final Logger logger = LogsCenter.getLogger(StandardCompilerEnvironment.class);

    // Location path at which new files are created and written
    private final Path locationPath;

    // List of previous created Java files
    private final List<JavaFile> createdFiles;

    private boolean isClosed;

    /**
     * Instantiates a new Standard compiler environment.
     *
     * @param locationPath the location path
     * @throws CreateEnvironmentException the create environment exception
     */
    public StandardCompilerEnvironment(Path locationPath) throws CreateEnvironmentException {
        this.locationPath = locationPath;
        this.createdFiles = new ArrayList<>();
        this.createDirectory(this.locationPath);

        this.isClosed = false;
    }

    @Override
    public JavaFile createJavaFile(UserProgram program) throws JavaFileCreationException,
            IncorrectCanonicalNameException {
        if (this.isClosed) {
            throw new ClosedEnvironmentException();
        }

        // Create empty Java file
        String canonicalName = program.getCanonicalName();
        File file = this.createEmptyJavaFile(canonicalName);

        // Write program contents to the Java file
        String sourceCode = program.getSourceCode();
        this.writeProgramToJavaFile(file, sourceCode);

        // Returns the newly created file as application's JavaFile model after adding it to the list of created files
        try {
            JavaFile javaFile = new JavaFile(canonicalName, locationPath.toString());
            this.createdFiles.add(javaFile);
            return javaFile;
        } catch (FileNotFoundException e) {
            throw new JavaFileCreationException(messageCreateJavaFileFailed + file.toPath(), e);
        }
    }

    @Override
    public JavaFile getJavaFile(String canonicalName) throws FileNotFoundException {
        if (this.isClosed) {
            throw new ClosedEnvironmentException();
        }

        // Search and return JavaFile in list of created files
        Optional<JavaFile> file = this.createdFiles.stream()
                .filter(javaFile -> javaFile.getCanonicalName().equals(canonicalName))
                .findFirst();

        return file.orElseThrow(() -> new FileNotFoundException(messageJavaFileNotFound + canonicalName));
    }

    @Override
    public void clearEnvironment() throws ClearEnvironmentException {
        if (this.isClosed) {
            throw new ClosedEnvironmentException();
        }

        try {
            logger.info("Clearing files and folder at : " + locationPath);
            // Traverse and delete created files and directories excluding the root folder
            Files.walk(locationPath)
                    .filter(path -> !path.equals(locationPath))
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);

            // Discard any references to previously created files
            this.createdFiles.clear();

            assert(Objects.requireNonNull(locationPath.toFile().listFiles()).length == 0);

            logger.info("Environment successfully cleared : " + locationPath);
        } catch (IOException e) {
            logger.warning(messageClearEnvironmentFailed + locationPath);
            throw new ClearEnvironmentException(messageClearEnvironmentFailed + locationPath, e);
        }
    }

    @Override
    public void close() {
        if (this.isClosed) {
            throw new ClosedEnvironmentException();
        }

        try {
            logger.info("Clearing files and folder at : " + locationPath);
            // Traverse and delete created files and directories including the root folder
            Files.walk(locationPath)
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);

            // Discard any references to previously created files
            this.createdFiles.clear();

            assert(!this.locationPath.toFile().exists());

            logger.info("Environment successfully closed : " + locationPath);
        } catch (IOException e) {
            logger.warning(messageClearEnvironmentFailed + locationPath);
            logger.warning("Environment not closed properly, remnant files may persist at : " + locationPath);
        } finally {
            this.isClosed = true;
        }
    }

    /**
     * Creates a new directory at the specified path on the user's file system.
     *
     * @param directoryPath the path at which to create the directory
     * @throws CreateEnvironmentException if the directory fails to be created
     */
    private void createDirectory(Path directoryPath) throws CreateEnvironmentException {
        try {
            File directory = directoryPath.toFile();
            if (!directory.exists() && !directory.mkdirs()) {
                logger.warning(messageUnableToCreateFolder + directoryPath);
                throw new CreateEnvironmentException(messageUnableToCreateFolder + directoryPath);
            }

            assert(directory.exists());

            logger.info("Created new folder at : " + directoryPath);
        } catch (SecurityException e) {
            logger.warning("SecurityException encountered. " + messageUnableToCreateFolder + directoryPath);
            throw new CreateEnvironmentException(messageUnableToCreateFolder + directoryPath, e);
        }
    }

    /**
     * Creates an empty Java file in the environment according to its canonical name together with the
     * corresponding directories. For example, foo.bar.Test would be created as in .../foo/bar/Test.java.
     *
     * @param canonicalName the canonical name of the Java class
     * @return the Java file created
     * @throws JavaFileCreationException if the file creation fails.
     */
    private File createEmptyJavaFile(String canonicalName) throws JavaFileCreationException {
        // Split the canonical name into individual subpackages and create a corresponding path.
        Path filePath = Arrays.stream(canonicalName.split("\\."))
                .reduce(this.locationPath, Path::resolve, Path::resolve);

        // Add .java extension to the file path
        filePath = filePath.resolveSibling(filePath.getFileName() + ".java");
        File file = filePath.toFile();

        logger.info("Creating empty Java file at : " + filePath);

        // Create the directories and file
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            logger.warning(messageCreateJavaFileFailed + filePath.toString());
            throw new JavaFileCreationException(messageCreateJavaFileFailed + filePath, e);
        }

        // Verify that the file exists before returning
        if (!file.exists()) {
            logger.warning(messageCreateJavaFileFailed + filePath.toString());
            throw new JavaFileCreationException(messageCreateJavaFileFailed + filePath);
        }

        logger.info("Java file created: " + file.getPath());

        return file;
    }

    /**
     * Writes a user program's source code into a java file. If the write fails, the file is deleted.
     *
     * @param javaFile the Java file to write the code to
     * @param program  the source code of the program
     * @throws JavaFileCreationException if the program write fails
     */
    private void writeProgramToJavaFile(File javaFile, String program) throws JavaFileCreationException {
        logger.info("Writing user program to file: " + javaFile.getPath());

        try {
            // Get an output writer to the file
            FileOutputStream fileOutputStream = new FileOutputStream(javaFile);
            Writer fileWriter = new OutputStreamWriter(fileOutputStream);

            // Write the source code into the file
            fileWriter.write(program);
            fileWriter.close();

        } catch (IOException e) {
            // If file write fails, the file is deleted before an exception is thrown
            logger.warning(messageWriteUserProgramFailed + javaFile.toPath());
            if (!javaFile.delete()) {
                logger.warning("Unable to delete unwritten file : " + javaFile.toPath());
            }
            throw new JavaFileCreationException(messageWriteUserProgramFailed + javaFile.toPath(), e);
        }

        logger.info("User program successfully written to file: " + javaFile.getPath());
    }
}
