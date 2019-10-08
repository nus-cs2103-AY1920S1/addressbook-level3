package com.dukeacademy.compiler.environment;

import com.dukeacademy.compiler.exceptions.CompilerEnvironmentException;
import com.dukeacademy.compiler.exceptions.FileCreationException;
import com.dukeacademy.compiler.exceptions.FileDeletionException;
import com.dukeacademy.compiler.exceptions.FileDirectoryCreationException;
import com.dukeacademy.compiler.exceptions.FileDirectoryDeletionException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * The standard environment class used by the compiler to perform file management.
 */
public class StandardCompilerEnvironment implements CompilerEnvironment {
    private String MESSAGE_ENVIRONMENT_NOT_INITIALIZED = "Compiler environment failed to initialized.";
    private String MESSAGE_ENVIRONMENT_NOT_CLOSED = "Compiler environment failed to close, remnant files may persist.";
    private String MESSAGE_ENVIRONMENT_NOT_CLEARED = "Compiler environment could not be cleared.";

    private StandardCompilerFileManager fileManager;
    private boolean isInitialized;

    public boolean isInitialized() {
        return this.isInitialized;
    }

    public String getEnvironmentPath() {
        return this.fileManager.getDirectoryPath();
    }

    @Override
    public void initiate(String locationPath) throws CompilerEnvironmentException {
        try {
            this.fileManager = new StandardCompilerFileManager(locationPath);
            this.isInitialized = true;
        } catch (FileDirectoryCreationException e) {
            throw new CompilerEnvironmentException(e.getMessage(), e);
        }
    }

    @Override
    public void createJavaFile(String name, String content) throws FileCreationException, CompilerEnvironmentException {
        if (!this.isInitialized) {
            throw new CompilerEnvironmentException(MESSAGE_ENVIRONMENT_NOT_INITIALIZED);
        }

        this.fileManager.createFile(name + ".java", content);
    }

    @Override
    public File getJavaFile(String name) throws FileNotFoundException, CompilerEnvironmentException {
        if (!this.isInitialized) {
            throw new CompilerEnvironmentException(MESSAGE_ENVIRONMENT_NOT_INITIALIZED);
        }

        return this.fileManager.getFile(name + ".java");
    }

    @Override
    public void clearEnvironment() throws CompilerEnvironmentException {
        if (!this.isInitialized) {
            throw new CompilerEnvironmentException(MESSAGE_ENVIRONMENT_NOT_INITIALIZED);
        }

        try {
            this.fileManager.clearDirectory();
        } catch (FileDeletionException e) {
            throw new CompilerEnvironmentException(MESSAGE_ENVIRONMENT_NOT_CLEARED);
        }
    }

    @Override
    public void close() throws CompilerEnvironmentException {
        if (!this.isInitialized) {
            throw new CompilerEnvironmentException(MESSAGE_ENVIRONMENT_NOT_INITIALIZED);
        }

        try {
            this.fileManager.deleteDirectory();
        } catch (FileDirectoryDeletionException e) {
            throw new CompilerEnvironmentException(MESSAGE_ENVIRONMENT_NOT_CLOSED);
        }
    }
}
