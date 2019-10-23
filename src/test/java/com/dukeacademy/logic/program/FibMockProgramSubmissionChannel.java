package com.dukeacademy.logic.program;

import com.dukeacademy.model.question.UserProgram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FibMockProgramSubmissionChannel implements ProgramSubmissionChannel {
    @Override
    public UserProgram getProgram()  {
        Path program = Paths.get("src", "test", "data", "TestPrograms", "fib", "fib.txt");
        try {
            String sourceCode = Files.readString(program);
            return new UserProgram("Fib", sourceCode);
        } catch (IOException e) {
            return null;
        }
    }
}
