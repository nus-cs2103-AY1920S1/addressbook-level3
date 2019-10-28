package com.dukeacademy.storage.questions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.storage.question.JsonAdaptedTestCase;

class JsonAdaptedTestCaseTest {
    @Test void toModel() throws Exception {
        TestCase testCase = new JsonAdaptedTestCase("1", "1").toModel();
        assertEquals("1", testCase.getInput());
        assertEquals("1", testCase.getExpectedResult());
    }

    @Test void toModelInvalid() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedTestCase(null, "1").toModel());
        assertThrows(NullPointerException.class, () -> new JsonAdaptedTestCase("1", null).toModel());
    }
}
