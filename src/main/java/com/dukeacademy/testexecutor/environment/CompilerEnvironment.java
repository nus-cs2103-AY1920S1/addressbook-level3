package com.dukeacademy.testexecutor.environment;

import java.io.FileNotFoundException;

import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.testexecutor.environment.exceptions.ClearEnvironmentException;
import com.dukeacademy.testexecutor.environment.exceptions.JavaFileCreationException;
import com.dukeacademy.testexecutor.exceptions.IncorrectCanonicalNameException;
import com.dukeacademy.testexecutor.models.JavaFile;

/**
 * Represents an environment needed for the compiler create and compile Java files from the user's input. It contains
 * methods to create and retrieve Java files from within the environment. The environment should maintain an open state
 * upon creation and a closed state upon closing. Operations should not be supported after the environment is closed.
 */
public interface CompilerEnvironment {
    /**
     * Creates a Java file within the compiler environment. The canonical name described in the UserProgram instance
     * must match its source code. E.g. if the canonical name is dukeacademy.testexecutor.CompilerEnvironment, it must
     * have the package statement "package dukeacademy.testexecutor" and it must contain
     * an outer class CompilerEnvironment.
     * @param program the program to be converted to a Java file
     * @return the file created
     * @throws JavaFileCreationException if the file creation fails
     * @throws IncorrectCanonicalNameException if the canonical name derived from the program does not
     * match its source code.
     */
    public JavaFile createJavaFile(UserProgram program) throws JavaFileCreationException,
            IncorrectCanonicalNameException;

    /**
     * Returns the file corresponding to the canonical name provided
     * @param canonicalName the name of the file
     * @return the file corresponding to the name provided
     * @throws FileNotFoundException if the file does not exists
     */
    public JavaFile getJavaFile(String canonicalName) throws FileNotFoundException;

    /**
     * Clears the environment of any existing files and folders
     * @throws ClearEnvironmentException if the environment cannot be cleared
     */
    public void clearEnvironment() throws ClearEnvironmentException;

    /**
     * Closes the environment by deleting the directory at which it was created.
     */
    public void close();
}
