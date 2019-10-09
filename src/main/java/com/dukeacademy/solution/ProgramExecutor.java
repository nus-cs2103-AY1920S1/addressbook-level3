package com.dukeacademy.solution;

import com.dukeacademy.solution.exceptions.FileNotClassException;
import com.dukeacademy.solution.exceptions.ProgramExecutorException;
import com.dukeacademy.solution.models.ProgramInput;
import com.dukeacademy.solution.models.ProgramOutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProgramExecutor {
    private static final String MESSAGE_PROGRAM_NOT_CLASS_FILE = "The program is not a Java class file.";
    private static final String MESSAGE_PROGRAM_EXECUTION_FAILED = "The program failed to execute.";

    private Runtime runtime;

    public ProgramExecutor() {
        this.runtime = Runtime.getRuntime();
    }

    public ProgramOutput executeProgram(File program, ProgramInput input) throws ProgramExecutorException {
        Process process = this.getExecutionProcess(program);
        this.feedProgramInput(process, input);

        return this.getProgramOutput(process);
    }

    public ProgramOutput executeProgram(File program) throws ProgramExecutorException {
        Process process = this.getExecutionProcess(program);
        return this.getProgramOutput(process);
    }

    private Process getExecutionProcess(File program) throws ProgramExecutorException {
        String className;
        String classPath;

        try {
            className = CompilerPathUtils.getClassName(program);
            classPath = CompilerPathUtils.getClassPath(program);
        } catch (FileNotClassException e) {
            throw new ProgramExecutorException(MESSAGE_PROGRAM_NOT_CLASS_FILE);
        }

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

    private void feedProgramInput(Process process, ProgramInput programInput) throws ProgramExecutorException {
        OutputStream stdin = process.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        try {
            String[] inputs = programInput.getInput().split("\\r?\\n");

            for (String input : inputs) {
                writer.write(input);
                writer.flush();
            }

            writer.close();
        } catch (IOException e) {
            throw new ProgramExecutorException(MESSAGE_PROGRAM_EXECUTION_FAILED);
        }
    }
}
