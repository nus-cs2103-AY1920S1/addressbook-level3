package com.dukeacademy.compiler.environment;

import com.dukeacademy.compiler.exceptions.CompilerContentException;
import com.dukeacademy.compiler.exceptions.CompilerEnvironmentException;
import com.dukeacademy.compiler.exceptions.FileCreationException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents the environment needed for the compiler create and compile Java files from the user's input.
 */
public interface CompilerEnvironment {
    /**
     * Sets up the compiler environment at the specified location path.
     * @param locationPath path of the location to set up the environment.
     * @throws CompilerEnvironmentException if the environment fails to set up.
     */
    public void initiate(String locationPath) throws CompilerEnvironmentException;

    /**
     * Creates a Java file within the compiler environment.
     * @param name the name of the file to be created, without extension.
     * @param content the contents of the Java file.
     * @throws CompilerContentException if the contents provided is invalid.
     */
    public void createJavaFile(String name, String content) throws CompilerContentException, FileCreationException, CompilerEnvironmentException;

    /**
     * Returns the file corresponding to the name provided.
     * @param name the name of the file, without extension.
     * @return the file corresponding to the name provided.
     * @throws FileNotFoundException if the file does not exists.
     */
    public File getJavaFile(String name) throws FileNotFoundException, CompilerEnvironmentException;

    /**
     * Clears the environment of any existing files and artifacts.
     * @throws CompilerEnvironmentException if the environment cannot be cleared.
     */
    public void clearEnvironment() throws CompilerEnvironmentException;

    /**
     * Closes the environment by deleting any created files or directories at the specified location path during
     * initialization.
     * @throws CompilerEnvironmentException if the environment cannot be closer properly.
     */
    public void close() throws CompilerEnvironmentException;
}
