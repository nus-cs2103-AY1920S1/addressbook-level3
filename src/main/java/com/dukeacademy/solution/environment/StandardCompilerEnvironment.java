package com.dukeacademy.solution.environment;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.solution.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solution.exceptions.CompilerFileCreationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.logging.Logger;

/**
 * The standard environment class used by the compiler to perform file management.
 */
public class StandardCompilerEnvironment implements CompilerEnvironment {
    private String MESSAGE_NO_PERMISSIONS = "Not permitted to create compiler environment in the following location.";
    private String MESSAGE_JAVA_FILE_NOT_FOUND = "No such file found in the compiler environment.";
    private String MESSAGE_CREATE_ENVIRONMENT_FAILED = "Failed to create compiler environment.";
    private String MESSAGE_CREATE_JAVA_FILE_FAILED = "Failed to create java file";
    private String MESSAGE_WRITE_JAVA_FILE_FAILED = "Failed to write to java file";
    private String MESSAGE_CLEAR_ENVIRONMENT_FAILED = "Compiler environment not cleared, remnant files may persist";

    private Logger logger = LogsCenter.getLogger(StandardCompilerEnvironment.class);
    private Path locationPath;

    public StandardCompilerEnvironment(String locationPath) throws CompilerEnvironmentException {
        this.locationPath = Path.of(locationPath);

        boolean isCreateDirectorySuccessful;

        try {
            String directoryPath = this.locationPath.toUri().getPath();
            isCreateDirectorySuccessful = new File(directoryPath).mkdir();
        } catch (SecurityException e) {
            throw new CompilerEnvironmentException(MESSAGE_NO_PERMISSIONS, e);
        }

        if (!isCreateDirectorySuccessful) {
            throw new CompilerEnvironmentException(MESSAGE_CREATE_ENVIRONMENT_FAILED);
        }
    }

    public String getLocationPath() {
        return this.locationPath.toUri().getPath();
    }

    @Override
    public File createJavaFile(String name, String content) throws CompilerFileCreationException {
        try {
            this.clearEnvironment();
        } catch (CompilerEnvironmentException e) {
            throw new CompilerFileCreationException(MESSAGE_CREATE_JAVA_FILE_FAILED);
        }

        String fileUri = this.locationPath.resolve(name + ".java").toUri().getPath();
        File file = new File(fileUri);

        boolean isFileCreationSuccessful;

        try {
            isFileCreationSuccessful = file.createNewFile();
        } catch (IOException e) {
            throw new CompilerFileCreationException(MESSAGE_CREATE_JAVA_FILE_FAILED);
        }

        if (!isFileCreationSuccessful) {
            throw new CompilerFileCreationException(MESSAGE_CREATE_JAVA_FILE_FAILED);
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Writer fileWriter = new OutputStreamWriter(fileOutputStream);

            fileWriter.write(content);

            fileWriter.close();
        } catch (IOException e) {
            this.clearDirectoryAfterFileWriteFailed();
            throw new CompilerFileCreationException(MESSAGE_CREATE_JAVA_FILE_FAILED);
        }

        return file;
    }

    private void clearDirectoryAfterFileWriteFailed() throws CompilerFileCreationException {
        try {
            this.clearEnvironment();
        } catch (CompilerEnvironmentException e) {
            throw new CompilerFileCreationException(MESSAGE_WRITE_JAVA_FILE_FAILED + " " + MESSAGE_CLEAR_ENVIRONMENT_FAILED);
        }
    }

    @Override
    public File getJavaFile(String name) throws FileNotFoundException {
        String filePath = this.locationPath.resolve(name + ".java").toUri().getPath();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException(MESSAGE_JAVA_FILE_NOT_FOUND);
        }

        return file;
    }

    @Override
    public void clearEnvironment() throws CompilerEnvironmentException {
        try {
            Files.walk(locationPath)
                    .filter(path -> !path.equals(locationPath))
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new CompilerEnvironmentException(MESSAGE_CLEAR_ENVIRONMENT_FAILED);
        }
    }

    @Override
    public void close() {
        try {
            Files.walk(locationPath)
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);
        } catch (IOException e) {
            logger.fine(MESSAGE_CLEAR_ENVIRONMENT_FAILED);
        }
    }
}
