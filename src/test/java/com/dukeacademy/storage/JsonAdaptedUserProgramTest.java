package com.dukeacademy.storage;

import com.dukeacademy.model.program.UserProgram;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonAdaptedUserProgramTest {
    private String className = "Test";
    private String content = "package foo.bar;\n"
            + "public class Test {\n}";

    @Test
    public void toModel() {
        UserProgram program = new JsonAdaptedUserProgram(className, content).toModel();
        assertEquals(className, program.getClassName());
        assertEquals(content, program.getSourceCodeAsString());
    }

    @Test
    public void toModelInvalid() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedUserProgram(null, content));
        assertThrows(NullPointerException.class, () -> new JsonAdaptedUserProgram(className, null));
    }
}
