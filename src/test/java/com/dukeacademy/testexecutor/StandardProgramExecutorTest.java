package com.dukeacademy.testexecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import com.dukeacademy.testexecutor.executor.StandardProgramExecutor;
import com.dukeacademy.testexecutor.executor.exceptions.ProgramExecutorException;
import com.dukeacademy.testexecutor.models.ClassFile;
import com.dukeacademy.testexecutor.models.ProgramInput;
import com.dukeacademy.testexecutor.models.ProgramOutput;

class StandardProgramExecutorTest {
    private static final Path testProgramsFolder = Paths.get("src", "test", "data", "TestPrograms",
            "ProgramExecutor");

    @Test void testExecuteValidProgramNoInput() throws IOException, ProgramExecutorException, ExecutionException,
            InterruptedException {
        StandardProgramExecutor executor = new StandardProgramExecutor();

        ClassFile program = new ClassFile("NoInputTest", testProgramsFolder.toString());
        ProgramOutput output = executor.executeProgram(program, new ProgramInput("")).get();

        String expectedOutput = Files.readString(testProgramsFolder.resolve("NoInputTestResult.txt"));

        assertFalse(output.getRuntimeError().isPresent());
        assertEquals(expectedOutput, output.getOutput());
    }

    @Test void testExecuteValidProgramWithInput() throws IOException, ProgramExecutorException,
            ExecutionException, InterruptedException {
        StandardProgramExecutor executor = new StandardProgramExecutor();

        ClassFile program = new ClassFile("WithInputTest", testProgramsFolder.toString());
        String input = Files.readString(testProgramsFolder.resolve("Input.txt"));
        ProgramOutput output = executor.executeProgram(program, new ProgramInput(input)).get();

        String expectedOutput = Files.readString(testProgramsFolder.resolve("WithInputTestResult.txt"));
        assertFalse(output.getRuntimeError().isPresent());
        assertEquals(expectedOutput, output.getOutput());
    }

    @Test void testExecuteProgramRuntimeError() throws ProgramExecutorException, FileNotFoundException,
            ExecutionException, InterruptedException {
        StandardProgramExecutor executor = new StandardProgramExecutor();

        ClassFile programClassFile = new ClassFile("OutOfBounds", testProgramsFolder.toString());

        ProgramOutput output = executor.executeProgram(programClassFile, new ProgramInput("")).get();
        assertTrue(output.getRuntimeError().isPresent());
    }
}
