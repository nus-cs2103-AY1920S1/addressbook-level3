package com.dukeacademy.solution.program;

import com.dukeacademy.solution.exceptions.ProgramExecutorException;
import com.dukeacademy.solution.models.ClassFile;
import com.dukeacademy.solution.models.ProgramInput;
import com.dukeacademy.solution.models.ProgramOutput;

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
