package com.typee.logic.commands.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NullUndoableActionExceptionTest {
    private static final String EXPECTED_MESSAGE = "No command to undo!";

    @Test
    public void messageTest() {
        assertEquals(new NullUndoableActionException().getMessage(), EXPECTED_MESSAGE);
    }
}
