package com.dukeacademy.checker.environment;

import com.dukeacademy.checker.exceptions.FileCreationException;
import com.dukeacademy.checker.exceptions.FileDeletionException;
import com.dukeacademy.checker.exceptions.FileDirectoryCreationException;
import com.dukeacademy.checker.exceptions.FileDirectoryDeletionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * Helper class for StandardCompilerEnvironment to perform file operations.
 */
public class StandardCompilerFileManager {
    private static String MESSAGE_NO_PERMISSIONS = "No permission to access the location path";
    private static String MESSAGE_CREATE_FILE_FAILED = "Failed to create Java file.";
    private static String MESSAGE_CREATE_DIRECTORY_FAILED = "Failed to create directory.";
    private static String MESSAGE_FILE_NOT_FOUND = "No such file was found.";
    private static String MESSAGE_DIRECTORY_CANNOT_BE_DELETED = "Directory cannot be deleted";
    private static String MESSAGE_DIRECTORY_CANNOT_BE_CLEARED = "Directory cannot be cleared";

    private Path locationPath;

    public String getDirectoryPath() {
        return this.locationPath.toUri().getPath();
    }

    StandardCompilerFileManager(String locationPath) throws FileDirectoryCreationException {
        this.locationPath = Path.of(locationPath);

        boolean createDirectorySuccessful;

        try {
            String directoryPath = this.locationPath.toUri().getPath();
            createDirectorySuccessful = new File(directoryPath).mkdir();
        } catch (SecurityException e) {
            throw new FileDirectoryCreationException(MESSAGE_NO_PERMISSIONS, e);
        }

        if (!createDirectorySuccessful) {
            throw new FileDirectoryCreationException(MESSAGE_CREATE_DIRECTORY_FAILED);
        }
    }

    File createFile(String fileName, String content) throws FileCreationException {
        String filePath = this.locationPath.resolve(fileName).toUri().getPath();
        File file = new File(filePath);

        boolean fileCreationSuccessful;

        try {
            fileCreationSuccessful = file.createNewFile();
        } catch (IOException e) {
            throw new FileCreationException(MESSAGE_CREATE_FILE_FAILED);
        }

        if (!fileCreationSuccessful) {
            throw new FileCreationException(MESSAGE_CREATE_FILE_FAILED);
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Writer fileWriter = new OutputStreamWriter(fileOutputStream);

            fileWriter.write(content);

            fileWriter.close();
        } catch (IOException e) {
            throw new FileCreationException(MESSAGE_CREATE_FILE_FAILED);
        }

        return file;
    }

    File getFile(String fileName) throws FileNotFoundException {
        String filePath = this.locationPath.resolve(fileName).toUri().getPath();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException(MESSAGE_FILE_NOT_FOUND);
        }

        return file;
    }

    void clearDirectory() throws FileDeletionException {
        try {
            Files.walk(locationPath)
                    .filter(path -> !path.equals(locationPath))
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new FileDeletionException(MESSAGE_DIRECTORY_CANNOT_BE_CLEARED);
        }
    }

    void deleteDirectory() throws FileDirectoryDeletionException {
        try {
            Files.walk(locationPath)
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new FileDirectoryDeletionException(MESSAGE_DIRECTORY_CANNOT_BE_DELETED);
        }
    }
}
