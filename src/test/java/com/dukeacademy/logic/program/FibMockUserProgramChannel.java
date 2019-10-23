package com.dukeacademy.logic.program;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.dukeacademy.model.question.UserProgram;

/**
 * Mock implementation of a program submission channel for testing.
 */
public class FibMockUserProgramChannel implements UserProgramChannel {
    @Override
    public UserProgram getProgram() {
        Path program = Paths.get("src", "test", "data", "TestPrograms", "fib", "fib.txt");
        try {
            String sourceCode = Files.readString(program);
            return new UserProgram("Fib", sourceCode);
        } catch (IOException e) {
            return null;
        }
    }
}
