package com.typee.logic.commands.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GenerateExistingReportExceptionTest {
    private static final String EXPECTED_MESSAGE = "Report already exists in the system!";

    @Test
    public void messageTest() {
        assertEquals(new GenerateExistingReportException().getMessage(), EXPECTED_MESSAGE);
    }
}
