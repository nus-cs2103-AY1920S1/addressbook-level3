package com.dukeacademy.solution;

import com.dukeacademy.solution.exceptions.FileNotClassException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CompilerPathUtilsTest {

    private File validTestFile1 = new File("abc.class");
    private File validTestFile2 = new File("/home/user/temp/foo.class");
    private File validTestFile3 = new File("/123/bar.class");
    private File invalidTestFile1 = new File("foobar");
    private File invalidTestFile2 = new File("x@!2%");

    @Test
    void isClassFile() {
        assertTrue(CompilerPathUtils.isClassFile(validTestFile1));
        assertTrue(CompilerPathUtils.isClassFile(validTestFile2));
        assertTrue(CompilerPathUtils.isClassFile(validTestFile3));

        assertFalse(CompilerPathUtils.isClassFile(invalidTestFile1));
        assertFalse(CompilerPathUtils.isClassFile(invalidTestFile2));
    }

    @Test
    void getClassName() throws FileNotClassException {
        assertEquals("abc", CompilerPathUtils.getClassName(validTestFile1));
        assertEquals("foo", CompilerPathUtils.getClassName(validTestFile2));
        assertEquals("bar", CompilerPathUtils.getClassName(validTestFile3));

        assertThrows(FileNotClassException.class, () -> CompilerPathUtils.getClassName(invalidTestFile1));
        assertThrows(FileNotClassException.class, () -> CompilerPathUtils.getClassName(invalidTestFile2));
    }

    @Test
    void getClassPath() throws FileNotClassException {
        assertEquals("", CompilerPathUtils.getClassPath(validTestFile1));
        assertEquals("/home/user/temp/", CompilerPathUtils.getClassPath(validTestFile2));
        assertEquals("/123/", CompilerPathUtils.getClassPath(validTestFile3));

        assertThrows(FileNotClassException.class, () -> CompilerPathUtils.getClassName(invalidTestFile1));
        assertThrows(FileNotClassException.class, () -> CompilerPathUtils.getClassName(invalidTestFile2));
    }
}