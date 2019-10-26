package com.dukeacademy.testexecutor.models;

import static com.dukeacademy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ProgramInputTest {

    @Test
    void testConstructorAndGetter() {
        String testString = "This is a test String!";
        ProgramInput input = new ProgramInput(testString);
        assertEquals(testString, input.getInput());

        assertThrows(NullPointerException.class, () -> new ProgramInput(null));
    }

    @Test
    void testEquals() {
        String testString = "This is a test String!";
        String alternateTestString = "This is an alternate test String!";
        assertEquals(new ProgramInput(testString), new ProgramInput(testString));
        assertNotEquals(new ProgramInput(testString), new ProgramInput(alternateTestString));
    }
}
