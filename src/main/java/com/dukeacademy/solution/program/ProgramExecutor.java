package com.dukeacademy.solution.program;

import com.dukeacademy.solution.exceptions.ProgramExecutorException;
import com.dukeacademy.solution.models.ClassFile;
import com.dukeacademy.solution.models.ProgramInput;
import com.dukeacademy.solution.models.ProgramOutput;

public interface ProgramExecutor {
    public ProgramOutput executeProgram(ClassFile program, ProgramInput input) throws ProgramExecutorException;
    public ProgramOutput executeProgram(ClassFile program) throws ProgramExecutorException;
}
