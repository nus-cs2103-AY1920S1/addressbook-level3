package com.typee.logic.commands.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommandExceptionTest {
    private static final String DUPLICATE_MESSAGE = "This engagement clashes with an already existing one";
    private static final Throwable TYPICAL_EXCEPTION = new Exception();

    @Test
    public void fieldDuplicateMessage_throwDuplicateMessage() {
        assertEquals(new CommandException(DUPLICATE_MESSAGE).getMessage(),
                DUPLICATE_MESSAGE);
    }

    @Test
    public void duplicateMessageAndTypicalCause_throwDuplicateMessageAndTypicalCause() {
        assertEquals(new CommandException(DUPLICATE_MESSAGE, TYPICAL_EXCEPTION).getMessage(),
                DUPLICATE_MESSAGE);
        assertEquals(new CommandException(DUPLICATE_MESSAGE, TYPICAL_EXCEPTION).getCause(),
                TYPICAL_EXCEPTION);
    }
}
