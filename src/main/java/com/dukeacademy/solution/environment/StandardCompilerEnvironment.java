package com.dukeacademy.solution.environment;

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
import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.model.solution.UserProgram;
import com.dukeacademy.solution.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solution.exceptions.JavaFileCreationException;
import com.dukeacademy.solution.models.JavaFile;

/**
 * The standard implementation of the CompilerEnvironment interface that used by the application to create and manage
 * files required for testing the user's answers. The standard implementation uses Java's standard libraries for IO,
 * files and paths to generate and maintain these files.
 */
public class StandardCompilerEnvironment implements CompilerEnvironment {
    // Standard error messages
    private static final String messageJavaFileNotFound = "No such file found in the compiler environment";
    private static final String messageCreateEnvironmentFailed = "Failed to create compiler environment";
    private static final String messageCreateJavaFileFailed = "Failed to create java file";
    private static final String messageWriteJavaFileFailed = "Failed to write to java file";
    private static final String messageClearEnvironmentFailed = "Compiler environment not cleared, "
            + "remnant files may persist";
    private static final String messageNoPermissions = "Not permitted to create compiler environment in "
            + "the following location";

    // Logger used to report behavior of the environment
    private final Logger logger = LogsCenter.getLogger(StandardCompilerEnvironment.class);

    // Root path for the location of the environment
    private final Path locationPath;

    // List of previous created Java files
    private final List<JavaFile> createdFiles;

    public StandardCompilerEnvironment(String locationPath) throws CompilerEnvironmentException {
        this.locationPath = Path.of(locationPath);
        this.createdFiles = new ArrayList<>();
        this.createDirectory(this.locationPath);
    }

    @Override
    public JavaFile createJavaFile(UserProgram program) throws JavaFileCreationException {
        // Clear the environment of any files that may hinder the compilation of the program
        try {
            this.clearEnvironment();
        } catch (CompilerEnvironmentException e) {
            throw new JavaFileCreationException(messageClearEnvironmentFailed);
        }

        // Create empty Java file
        String canonicalName = program.getCanonicalName();
        File file = this.createEmptyJavaFileInEnvironment(canonicalName);

        // Write program contents to the Java file
        String sourCode = program.getSourceCodeAsString();
        this.writeProgramToJavaFile(file, sourCode);

        // Returns the newly created file as application's JavaFile model after adding it to the list of created files
        try {
            JavaFile javaFile = new JavaFile(canonicalName, this.getLocationPath());
            this.createdFiles.add(javaFile);
            return javaFile;
        } catch (FileNotFoundException e) {
            throw new JavaFileCreationException(messageCreateJavaFileFailed);
        }
    }

    @Override
    public JavaFile getJavaFile(String canonicalName) throws FileNotFoundException {
        // Search and return JavaFile in list of created files
        Optional<JavaFile> file = this.createdFiles.stream()
                .filter(javaFile -> javaFile.getCanonicalName().equals(canonicalName))
                .findFirst();

        return file.orElseThrow(() -> new FileNotFoundException(messageJavaFileNotFound));
    }

    @Override
    public void clearEnvironment() throws CompilerEnvironmentException {
        try {
            // Traverse and delete created files and directories excluding the root folder
            Files.walk(locationPath)
                    .filter(path -> !path.equals(locationPath))
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);

            // Discard any references to previously created files
            this.createdFiles.clear();
        } catch (IOException e) {
            throw new CompilerEnvironmentException(messageClearEnvironmentFailed);
        }
    }

    @Override
    public void close() {
        try {
            // Traverse and delete created files and directories including the root folder
            Files.walk(locationPath)
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);

            // Discard any references to previously created files
            this.createdFiles.clear();
        } catch (IOException e) {
            logger.fine(messageClearEnvironmentFailed);
        }
    }

    /**
     * Returns the root path of the environment as a String
     */
    public String getLocationPath() {
        return this.locationPath.toUri().getPath();
    }

    /**
     * Creates a new directory at the specified path.
     * @param path the path at which to create the directory.
     * @throws CompilerEnvironmentException if the directory fails to be created.
     */
    private void createDirectory(Path path) throws CompilerEnvironmentException {
        try {
            String directoryPath = path.toUri().getPath();
            if (!new File(directoryPath).mkdir()) {
                throw new CompilerEnvironmentException(messageCreateEnvironmentFailed);
            }

            logger.fine("Created root directory for compiler environment.");
        } catch (SecurityException e) {
            throw new CompilerEnvironmentException(messageNoPermissions, e);
        }
    }

    /**
     * Creates an empty Java file in the environment according to its canonical name together with the
     * corresponding directories. For example, foo.bar.Test would be created as in .../foo/bar/Test.java.
     * @param canonicalName the canonical name of the class of the Java file.
     * @return the Java file created.
     * @throws JavaFileCreationException if the file creation fails.
     */
    private File createEmptyJavaFileInEnvironment(String canonicalName) throws JavaFileCreationException {
        logger.fine("Creating temporary Java file: " + canonicalName);

        // Split the canonical name into individual subpackages and create a corresponding path.
        Path filePath = Arrays.stream(canonicalName.split("\\."))
                .reduce(this.locationPath, Path::resolve, Path::resolve);

        // Add .java extension to the file path
        filePath = filePath.resolveSibling(filePath.getFileName() + ".java");

        File file = filePath.toFile();

        // Create the directories and file
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            throw new JavaFileCreationException(messageCreateJavaFileFailed);
        }

        // Verify that the file exists before returning
        if (!file.exists()) {
            throw new JavaFileCreationException(messageCreateJavaFileFailed);
        }

        logger.fine("Java file created: " + canonicalName);

        return file;
    }

    /**
     * Writes a user's written program into a Java File as it is.
     * @param javaFile the Java file to write the program to.
     * @param program the source code of the program as a string.
     * @throws JavaFileCreationException if the program write fails.
     */
    private void writeProgramToJavaFile(File javaFile, String program) throws JavaFileCreationException {
        logger.fine("Writing source code to file: " + javaFile.getName());

        try {
            // Get an output writer to the file
            FileOutputStream fileOutputStream = new FileOutputStream(javaFile);
            Writer fileWriter = new OutputStreamWriter(fileOutputStream);

            // Write the source code into the file
            fileWriter.write(program);
            fileWriter.close();

        } catch (IOException e) {

            // If file write fails, the environment is cleared of the incomplete file before an exception is thrown
            if (!javaFile.delete()) {
                logger.fine("Could not delete Java file after write failed. File may persist on file system.");
            }
            throw new JavaFileCreationException(messageCreateJavaFileFailed);
        }

        logger.fine("Source code successfully written to file: " + javaFile.getName());
    }
}
