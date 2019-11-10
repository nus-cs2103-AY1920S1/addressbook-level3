package com.typee.logic.commands.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NullRedoableActionExceptionTest {
    private static final String EXPECTED_MESSAGE = "No command to redo!";

    @Test
    public void messageTest() {
        assertEquals(new NullRedoableActionException().getMessage(), EXPECTED_MESSAGE);
    }
}
