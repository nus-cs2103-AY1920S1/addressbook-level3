package com.dukeacademy.testexecutor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestExecutorUtilsTest {

    @Test
    void checkCanonicalNameMatchesProgram() {
        // Check without package statement

        // Without nested classes
        String canonicalName = "Main";
        String program = "public class Main { }";
        assertTrue(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName, program));

        // With package statement
        String canonicalName1 = "packaged.inside.Main";
        String program1 = "package packaged.inside;\n public class Main { }";
        assertTrue(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName1, program1));

        // With multiple outer classes of various order
        String canonicalName2 = "Main";
        String program2 = "public class Test {}\n public class Main{} \n public class Util{}";
        assertTrue(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName2, program2));

        // With nested classes
        String canonicalName3 = "packaged.inside.Main";
        String program3 = "package packaged.inside; \n class Main{public class Test{}}";
        assertTrue(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName3, program3));

        // With methods and attributes
        String canonicalName4 = "Main";
        String program4 = "public class Main { "
                + "public int x = 2; "
                + "public void sayHello() { System.out.println(\"hello\"} "
                + "}";
        assertTrue(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName4, program4));

        // Exceptions

        // No space after class keyword
        String canonicalName16 = "Main";
        String program16 = "public classMain{}";
        assertFalse(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName16, program16));

        // Class is an inner class
        String canonicalName17 = "packaged.inside.Main";
        String program17 = "package packaged.inside;\n public class Test { public class Main{}}";
        assertFalse(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName17, program17));

        // Package statement does not match
        String canonicalName18 = "packaged.inside.Main";
        String program18 = "package packaged.outside.Main;  \npublic class Main {}";
        assertFalse(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName18, program18));

        // Multiple classes with the same name
        String canonicalName19 = "Main";
        String program19 = "class Main{} class Main{}";
        assertTrue(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName19, program19));

        // Class name is incorrect
        String canonicalName20 = "Test";
        String program20 = "class Main{}";
        assertFalse(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName20, program20));

        // Missing closing brace
        String canonicalName21 = "Main";
        String program21 = "public class Util { } \n public class Main { public class Test {}\n";
        assertTrue(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName21, program21));

        // Inner class with missing opening brace
        String canonicalName22 = "Main";
        String program22 = "public class Util public class Main { } }";
        assertFalse(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName22, program22));

        // Missing braces
        String canonicalName23 = "Main";
        String program23 = "public class Test{} \n public class Main public class Util{}";
        assertTrue(TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName23, program23));
    }
}
