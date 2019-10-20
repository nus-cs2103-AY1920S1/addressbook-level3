package com.dukeacademy.model.program;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UserProgramTest {

    @Test
    public void getCanonicalName() {
        String fileName = "Test";
        String content = "package foo.bar;\n"
                + "public class Test {\n}";

        UserProgram program = new UserProgram(fileName, content);
        String canonicalName = program.getCanonicalName();

        assertEquals("foo.bar.Test", canonicalName);
    }

    @Test
    public void constructor() {
        assertThrows(IllegalArgumentException.class, () -> new UserProgram("  ", ""));
        assertThrows(IllegalArgumentException.class, () -> new UserProgram(null, null));
    }
}
