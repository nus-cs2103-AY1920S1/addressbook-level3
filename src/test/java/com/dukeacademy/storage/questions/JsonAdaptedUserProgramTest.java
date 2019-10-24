package com.dukeacademy.storage.questions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.storage.question.JsonAdaptedUserProgram;

public class JsonAdaptedUserProgramTest {
    private String className = "Test";
    private String content = "package foo.bar;\n"
            + "public class Test {\n}";

    @Test
    public void toModel() {
        UserProgram program = new JsonAdaptedUserProgram(className, content).toModel();
        assertEquals(className, program.getClassName());
        assertEquals(content, program.getSourceCode());
    }

    @Test
    public void toModelInvalid() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedUserProgram(null, content).toModel());
        assertThrows(NullPointerException.class, () -> new JsonAdaptedUserProgram(className, null).toModel());
    }
}
