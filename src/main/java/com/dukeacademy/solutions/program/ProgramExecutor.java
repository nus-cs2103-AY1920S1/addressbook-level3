package com.dukeacademy.solutions.program;

import com.dukeacademy.solutions.exceptions.ProgramExecutorException;
import com.dukeacademy.solutions.models.ClassFile;
import com.dukeacademy.solutions.models.ProgramInput;
import com.dukeacademy.solutions.models.ProgramOutput;

/**
 * Interface for an executor to run Java class files in the application.
 */
public interface ProgramExecutor {
    /**
     * Executes the given Java class file with the following input.
     * @param program the program to be executed.
     * @param input the input to be fed into the program.
     * @return the program output.
     * @throws ProgramExecutorException if the execution fails.
     */
    public ProgramOutput executeProgram(ClassFile program, ProgramInput input) throws ProgramExecutorException;

    /**
     * Executes the given Java class file with no input.
     * @param program the program to be executed.
     * @return the program output.
     * @throws ProgramExecutorException if the execution fails.
     */
    public ProgramOutput executeProgram(ClassFile program) throws ProgramExecutorException;
}
