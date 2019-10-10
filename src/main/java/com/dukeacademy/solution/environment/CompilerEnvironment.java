package com.dukeacademy.solution.environment;

import com.dukeacademy.model.solution.UserProgram;
import com.dukeacademy.solution.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solution.exceptions.CompilerFileCreationException;
import com.dukeacademy.solution.models.JavaFile;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents the environment needed for the compiler create and compile Java files from the user's input.
 */
public interface CompilerEnvironment {
    /**
     * Creates a Java file within the compiler environment.
     * @param program the program to be converted to a Java file.
     * @return the file created.
     */
    public JavaFile createJavaFile(UserProgram program) throws CompilerFileCreationException, CompilerEnvironmentException;

    /**
     * Returns the file corresponding to the name provided.
     * @param name the name of the file, without extension.
     * @return the file corresponding to the name provided.
     * @throws FileNotFoundException if the file does not exists.
     */
    public JavaFile getJavaFile(String name) throws FileNotFoundException, CompilerEnvironmentException;

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
