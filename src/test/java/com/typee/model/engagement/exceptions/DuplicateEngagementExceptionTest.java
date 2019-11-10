package com.typee.model.engagement.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DuplicateEngagementExceptionTest {
    @Test
    public void messageTest() {
        assertEquals(new DuplicateEngagementException().getMessage(),
                "Operation would result in duplicate engagements");
    }

    @Test
    public void classTest() {
        assertTrue(new DuplicateEngagementException() instanceof RuntimeException);
    }
}
