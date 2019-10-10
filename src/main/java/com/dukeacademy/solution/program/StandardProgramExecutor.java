package com.dukeacademy.solution.program;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

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

    private Process getExecutionProcess(ClassFile program) throws ProgramExecutorException {
        String className = program.getCanonicalName();
        String classPath = program.getClassPath();

        String command = "java -cp " + classPath + " " + className;

        try {
            return this.runtime.exec(command);
        } catch (IOException e) {
            throw new ProgramExecutorException(MESSAGE_PROGRAM_EXECUTION_FAILED);
        }
    }

    private ProgramOutput getProgramOutput(Process process) {
        InputStream stdout = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

        ProgramOutput programOutput = new ProgramOutput();
        List<String> outputs = reader.lines().collect(Collectors.toList());

        for (String output: outputs) {
            programOutput.appendNewLine(output);
        }

        return programOutput;
    }

    /**
     * Feeds input to an ongoing execution process.
     */
    private void feedProgramInput(Process process, ProgramInput programInput) throws ProgramExecutorException {
        OutputStream stdin = process.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        try {
            writer.write(programInput.getInput());
            writer.flush();

            writer.close();
        } catch (IOException e) {
            throw new ProgramExecutorException(MESSAGE_PROGRAM_EXECUTION_FAILED);
        }
    }
}
