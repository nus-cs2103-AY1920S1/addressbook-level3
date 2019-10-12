package com.dukeacademy.solution.program;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dukeacademy.solution.exceptions.ProgramExecutorException;
import com.dukeacademy.solution.models.ClassFile;
import com.dukeacademy.solution.models.ProgramInput;
import com.dukeacademy.solution.models.ProgramOutput;

/**
 * Standard executor for Java class files used by the application.
 */
public class StandardProgramExecutor implements ProgramExecutor {
    private static final String MESSAGE_PROGRAM_EXECUTION_FAILED = "The program failed to execute.";

    private Runtime runtime;

    public StandardProgramExecutor() {
        this.runtime = Runtime.getRuntime();
    }

    @Override
    public ProgramOutput executeProgram(ClassFile program, ProgramInput input) throws ProgramExecutorException {
        Process process = this.getExecutionProcess(program);
        this.feedProgramInput(process, input);

        return this.getProgramOutput(process);
    }

    @Override
    public ProgramOutput executeProgram(ClassFile program) throws ProgramExecutorException {
        Process process = this.getExecutionProcess(program);
        return this.getProgramOutput(process);
    }

    /**
     * Gets the execution process using Java's RunTime standard library.
     * @param program the program to be executed as a ClassFile.
     * @return the execution process.
     * @throws ProgramExecutorException if the program fails to be executed.
     */
    private Process getExecutionProcess(ClassFile program) throws ProgramExecutorException {
        // Generate the correct command for the process
        String className = program.getCanonicalName();
        String classPath = program.getClassPath();
        String command = "java -cp " + classPath + " " + className;

        try {
            return this.runtime.exec(command);
        } catch (IOException e) {
            throw new ProgramExecutorException(MESSAGE_PROGRAM_EXECUTION_FAILED);
        }
    }

    /**
     * Gets the application's model of a program output from an execution process.
     * @param process the executing process.
     * @return the program output of the process.
     */
    private ProgramOutput getProgramOutput(Process process) {
        // Retrieve the error stream of the process
        InputStream err = process.getErrorStream();

        // Read any errors
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(err));
        String error = errorReader.lines().collect(Collectors.joining());

        // Return errored program output if present
        if (!error.equals("")) {
            return ProgramOutput.getErroredProgramOutput(error);
        }

        // Retrieve the output stream of the process
        InputStream stdout = process.getInputStream();

        // Read the output of the process
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        ProgramOutput programOutput = new ProgramOutput();

        // Append the output to our model
        reader.lines().forEachOrdered(programOutput::appendNewLine);

        return programOutput;
    }

    /**
     * Feeds input into a currently executing execution process.
     * @param process the process to be fed.
     * @param programInput the inputs to be fed to the program.
     * @throws ProgramExecutorException if the input fails to be fed.
     */
    private void feedProgramInput(Process process, ProgramInput programInput) throws ProgramExecutorException {
        // Retrieve the input stream of the process
        OutputStream stdin = process.getOutputStream();

        // Try to write the input into the process
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
            writer.write(programInput.getInput());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new ProgramExecutorException(MESSAGE_PROGRAM_EXECUTION_FAILED);
        }
    }
}
