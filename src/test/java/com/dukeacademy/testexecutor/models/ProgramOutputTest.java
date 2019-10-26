package com.dukeacademy.testexecutor.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.dukeacademy.testexecutor.exceptions.ProgramOutputException;

class ProgramOutputTest {

    @Test
   void testStaticConstructorsAndGetters() {
        ProgramOutput emptyOutput = ProgramOutput.getEmptyProgramOutput();
        assertEquals("", emptyOutput.getOutput());
        assertEquals(Optional.empty(), emptyOutput.getRuntimeError());

        String errorMessage = "This is a test error message!";
        ProgramOutput errorProgramOutput = ProgramOutput.getErroredProgramOutput(errorMessage);
        assertEquals("", errorProgramOutput.getOutput());
        assertTrue(errorProgramOutput.getRuntimeError().isPresent());
        assertEquals(errorMessage, errorProgramOutput.getRuntimeError().get().getErrorMessage());
    }

    @Test
    void testAppend() {
        String errorMessage = "This is a test error message!";
        String append1 = "append1";
        String append2 = "append2";
        String append3 = "append3";

        ProgramOutput programAppend = ProgramOutput.getEmptyProgramOutput();

        // Errored program outputs should not have outputs
        ProgramOutput testOutput = ProgramOutput.getEmptyProgramOutput();
        ProgramOutput erroredProgramOutput = ProgramOutput.getErroredProgramOutput(errorMessage);
        assertThrows(ProgramOutputException.class, () -> erroredProgramOutput.append(append1));
        assertThrows(ProgramOutputException.class, () -> erroredProgramOutput.append(testOutput));

        // Tests for immutability
        programAppend.append(append1);
        assertEquals("", programAppend.getOutput());

        // Regular tests
        programAppend = programAppend.append(append1);
        assertEquals(append1, programAppend.getOutput());

        programAppend = programAppend.append(append2);
        assertEquals(append1 + append2, programAppend.getOutput());

        ProgramOutput testOutput1 = ProgramOutput.getEmptyProgramOutput().append(append3);
        programAppend = programAppend.append(testOutput1);
        assertEquals(append1 + append2 + append3, programAppend.getOutput());
    }

    @Test
    void testAppendNewLine() {
        String errorMessage = "This is a test error message!";
        String append1 = "append1";
        String append2 = "append2";
        String append3 = "append3";

        ProgramOutput programAppend = ProgramOutput.getEmptyProgramOutput();

        // Errored program outputs should not have outputs
        ProgramOutput testOutput = ProgramOutput.getEmptyProgramOutput();
        ProgramOutput erroredProgramOutput = ProgramOutput.getErroredProgramOutput(errorMessage);
        assertThrows(ProgramOutputException.class, () -> erroredProgramOutput.appendNewLine(append1));
        assertThrows(ProgramOutputException.class, () -> erroredProgramOutput.appendNewLine(testOutput));

        // Tests for immutability
        programAppend.appendNewLine(append1);
        assertEquals("", programAppend.getOutput());

        // Regular tests
        programAppend = programAppend.appendNewLine(append1);
        assertEquals(append1, programAppend.getOutput());

        programAppend = programAppend.appendNewLine(append2);
        assertEquals(append1 + "\n" + append2, programAppend.getOutput());

        ProgramOutput testOutput1 = ProgramOutput.getEmptyProgramOutput().append(append3);
        programAppend = programAppend.appendNewLine(testOutput1);
        assertEquals(append1 + "\n" + append2 + "\n" + append3, programAppend.getOutput());
    }

    @Test
    void testEquals() {
        String output = "This is an output!";
        String alternateOutput = "This is an alternate output!";

        assertEquals(ProgramOutput.getEmptyProgramOutput().append(output),
                ProgramOutput.getEmptyProgramOutput().append(output));
        assertNotEquals(ProgramOutput.getEmptyProgramOutput().append(output),
                ProgramOutput.getEmptyProgramOutput().append(alternateOutput));

        String errorMessage = "This is an error message!";
        String alternateErrorMessage = "This is an alternate error message!";

        assertEquals(ProgramOutput.getErroredProgramOutput(errorMessage),
                ProgramOutput.getErroredProgramOutput(errorMessage));
        assertNotEquals(ProgramOutput.getErroredProgramOutput(errorMessage),
                ProgramOutput.getErroredProgramOutput(alternateErrorMessage));
    }
}
