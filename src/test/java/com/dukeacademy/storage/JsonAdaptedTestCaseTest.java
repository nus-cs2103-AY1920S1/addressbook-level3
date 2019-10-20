package com.dukeacademy.storage;

import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.storage.question.JsonAdaptedTestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonAdaptedTestCaseTest {
    @Test
    public void toModel() throws Exception {
        TestCase testCase = new JsonAdaptedTestCase("1", "1").toModel();
        assertEquals("1", testCase.getInput());
        assertEquals("1", testCase.getExpectedResult());
    }

    @Test
    public void toModelInvalid() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedTestCase(null, "1").toModel());
        assertThrows(NullPointerException.class, () -> new JsonAdaptedTestCase("1", null).toModel());
    }
}
