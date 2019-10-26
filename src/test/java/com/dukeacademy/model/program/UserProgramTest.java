package com.dukeacademy.model.program;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.UserProgram;

class UserProgramTest {
    private String canonicalName = "foo.bar.Test";
    private String content = "package foo.bar;\n"
            + "public class Test {\n}";
    @Test
    public void getCanonicalName() {
        UserProgram program = new UserProgram(canonicalName, content);
        assertEquals("foo.bar.Test", program.getCanonicalName());
    }

    @Test
    public void constructor() {
        assertThrows(NullPointerException.class, () -> new UserProgram(null, null));
        UserProgram userProgram = new UserProgram(canonicalName, content);
        assertEquals(canonicalName, userProgram.getCanonicalName());
        assertEquals(content, userProgram.getSourceCode());
    }
}
