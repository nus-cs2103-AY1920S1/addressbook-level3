package com.dukeacademy.model.program;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.UserProgram;

class UserProgramTest {

    @Test void testConstructorAndGetters() {
        assertThrows(NullPointerException.class, () -> new UserProgram(null, null));
        String content = "package foo.bar;\n" + "public class Test {\n}";
        String canonicalName = "foo.bar.Test";
        UserProgram userProgram = new UserProgram(canonicalName, content);
        assertEquals(canonicalName, userProgram.getCanonicalName());
        assertEquals(content, userProgram.getSourceCode());
    }
}
