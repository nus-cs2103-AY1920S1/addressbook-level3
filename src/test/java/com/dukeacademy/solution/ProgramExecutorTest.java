package com.dukeacademy.solution;

import com.dukeacademy.model.solution.UserProgram;
import com.dukeacademy.solution.exceptions.ProgramExecutorException;
import com.dukeacademy.solution.models.ClassFile;
import com.dukeacademy.solution.models.ProgramInput;
import com.dukeacademy.solution.models.ProgramOutput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ProgramExecutorTest {
    static ProgramExecutor executor;

    static Path testProgramRootFolder = Paths.get("src", "test", "data", "TestPrograms");
    static String noInputTestName = "NoInputTest";
    static String withInputTestName = "WithInputTest";

    static String noInputTestOutput;
    static String withInputTestOutput;
    static String input;

    @BeforeAll
    static void initializeTest() throws IOException {
        noInputTestOutput = Files.readString(testProgramRootFolder.resolve("NoInputTestResult.txt"));
        withInputTestOutput = Files.readString(testProgramRootFolder.resolve("WithInputTestResult.txt"));
        input = Files.readString(testProgramRootFolder.resolve("Input.txt"));
        executor = new ProgramExecutor();
    }

    @Test
    void executeProgramNoInput() throws FileNotFoundException, ProgramExecutorException {
        ClassFile programClassFile = new ClassFile("NoInputTest", testProgramRootFolder.toUri().getPath());
        ProgramOutput output = executor.executeProgram(programClassFile);

        assertEquals(noInputTestOutput, output.getOutput());
    }

    @Test
    void executeProgramWithInput() throws FileNotFoundException, ProgramExecutorException {
        ClassFile programClassFile = new ClassFile("WithInputTest", testProgramRootFolder.toUri().getPath());

        ProgramInput programInput = new ProgramInput(input);
        ProgramOutput output = executor.executeProgram(programClassFile, programInput);

        assertEquals(withInputTestOutput, output.getOutput());
    }
}