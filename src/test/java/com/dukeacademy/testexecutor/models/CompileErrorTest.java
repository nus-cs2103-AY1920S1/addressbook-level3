package com.dukeacademy.testexecutor.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class CompileErrorTest {
    @Test
    void testConstructorAndGetter() {
        String errorMessage = "Hello, this is a compile error!";
        CompileError error = new CompileError(errorMessage);
        assertEquals(errorMessage, error.getErrorMessage());
    }

    @Test
    void testEquals() {
        String errorMessage = "Hello, this is a compile error!";
        String alternateErrorMessage = "Hello, this is an alternate compile error!";

        CompileError error1 = new CompileError(errorMessage);
        CompileError error2 = new CompileError(errorMessage);
        assertEquals(error1, error2);

        CompileError error3 = new CompileError(alternateErrorMessage);
        assertNotEquals(error1, error3);
    }
}
