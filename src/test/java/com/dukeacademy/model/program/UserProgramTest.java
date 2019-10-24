package com.dukeacademy.model.program;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.UserProgram;

class UserProgramTest {
    private String className = "Test";
    private String content = "package foo.bar;\n"
            + "public class Test {\n}";
    @Test
    public void getCanonicalName() {
        UserProgram program = new UserProgram(className, content);
        String canonicalName = program.getCanonicalName();

        assertEquals("foo.bar.Test", canonicalName);
    }

    @Test
    public void constructor() {
        assertThrows(IllegalArgumentException.class, () -> new UserProgram("  ", ""));
        assertThrows(NullPointerException.class, () -> new UserProgram(null, null));
        UserProgram userProgram = new UserProgram(className, content);
        assertEquals(className, userProgram.getClassName());
        assertEquals(content, userProgram.getSourceCode());
    }
}
