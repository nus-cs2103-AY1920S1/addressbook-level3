package com.typee.model.engagement.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InvalidTimeExceptionTest {
    private static final String MESSAGE = "Invalid time";

    @Test
    public void messageTest() {
        assertEquals(new InvalidTimeException(MESSAGE).getMessage(), MESSAGE);
    }

    @Test
    public void classTest() {
        assertTrue(new DuplicateEngagementException() instanceof Exception);
    }
}
