package com.dukeacademy.model.question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCaseTest {
    @Test
    public void constructor() {
        assertThrows(IllegalArgumentException.class, () -> new TestCase(null, null));
    }
}