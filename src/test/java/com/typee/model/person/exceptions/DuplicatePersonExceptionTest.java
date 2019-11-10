package com.typee.model.person.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DuplicatePersonExceptionTest {
    @Test
    public void messageTest() {
        assertEquals(new DuplicatePersonException().getMessage(), "Operation would result in duplicate persons");
    }

    @Test
    public void classTest() {
        assertTrue(new DuplicatePersonException() instanceof RuntimeException);
    }
}
