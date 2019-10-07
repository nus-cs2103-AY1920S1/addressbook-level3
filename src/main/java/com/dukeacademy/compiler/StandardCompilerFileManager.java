package com.dukeacademy.compiler;

import com.dukeacademy.compiler.exceptions.FileCreationException;
import com.dukeacademy.compiler.exceptions.FileDirectoryCreationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Helper class for StandardCompilerEnvironment to perform file operations.
 */
public class StandardCompilerFileManager {
    private static String MESSAGE_NO_PERMISSIONS = "No permission to access the location path";
    private static String MESSAGE_CREATE_FILE_FAILED = "Failed to create Java file.";
    private static String MESSAGE_CREATE_DIRECTORY_FAILED = "Failed to create directory.";
    private static String MESSAGE_FILE_NOT_FOUND = "No such file was found.";

    private String locationPath;

    public StandardCompilerFileManager(String locationPath) throws FileDirectoryCreationException {
        this.locationPath = locationPath;

        boolean createDirectorySuccessful;

        try {
            createDirectorySuccessful = new File(locationPath).mkdir();
        } catch (SecurityException e) {
            throw new FileDirectoryCreationException(MESSAGE_NO_PERMISSIONS);
        }

        if (!createDirectorySuccessful) {
            throw new FileDirectoryCreationException(MESSAGE_CREATE_DIRECTORY_FAILED);
        }
    }

    public File createFile(String fileName, String content) throws FileCreationException {
        String filePath = this.locationPath + File.separator + fileName;
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

        } catch (IOException e) {
            throw new FileCreationException(MESSAGE_CREATE_FILE_FAILED);
        }

        return file;
    }

    public File getFile(String fileName) throws FileNotFoundException {
        String filePath = this.locationPath + File.separator + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException(MESSAGE_FILE_NOT_FOUND);
        }

        return file;
    }

    public void clearDirectory() {
        new File(locationPath).delete();
    }
}
