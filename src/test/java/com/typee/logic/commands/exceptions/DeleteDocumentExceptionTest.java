package com.typee.logic.commands.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DeleteDocumentExceptionTest {
    private static final String EXPECTED_MESSAGE = "Document file to delete does not appear in the directory.";

    @Test
    public void messageTest() {
        assertEquals(new DeleteDocumentException().getMessage(), EXPECTED_MESSAGE);
    }
}
