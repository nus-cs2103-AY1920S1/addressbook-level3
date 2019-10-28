package com.dukeacademy.storage.questions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.storage.question.JsonAdaptedUserProgram;

class JsonAdaptedUserProgramTest {
    private final String className = "Test";
    private final String content = "package foo.bar;\n"
            + "public class Test {\n}";

    @Test void toModel() {
        UserProgram program = new JsonAdaptedUserProgram(className, content).toModel();
        assertEquals(className, program.getCanonicalName());
        assertEquals(content, program.getSourceCode());
    }

    @Test void toModelInvalid() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedUserProgram(null, content).toModel());
        assertThrows(NullPointerException.class, () -> new JsonAdaptedUserProgram(className, null).toModel());
    }
}
