package com.dukeacademy.testexecutor.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class RuntimeErrorTest {
    @Test
    void testConstructorAndGetter() {
        String errorMessage = "Hello, this is a runtime error!";
        RuntimeError error = new RuntimeError(errorMessage);
        assertEquals(errorMessage, error.getErrorMessage());
    }

    @Test
    void testEquals() {
        String errorMessage = "Hello, this is a compile error!";
        String alternateErrorMessage = "Hello, this is an alternate compile error!";

        RuntimeError error1 = new RuntimeError(errorMessage);
        RuntimeError error2 = new RuntimeError(errorMessage);
        assertEquals(error1, error2);

        RuntimeError error3 = new RuntimeError(alternateErrorMessage);
        assertNotEquals(error1, error3);
    }
}
