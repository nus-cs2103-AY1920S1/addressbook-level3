package com.dukeacademy.solution;

import com.dukeacademy.model.solution.UserProgram;
import com.dukeacademy.solution.models.ProgramInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ProgramExecutorTest {
    @TempDir
    private Path tempFolder;
    private ProgramExecutor executor;

    @BeforeEach
    void initializeTest() {

    }

    @Test
    void executeProgramNoInput() {

    }

    @Test
    void executeProgramWithInput() {
    }
}