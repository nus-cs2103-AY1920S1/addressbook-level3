package seedu.jarvis.commons.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;

public class CourseUtilTest {

    @Test
    public void getCourseJsonStringTest() {
        try {
            CourseUtil.getCourseJsonString("CS", "CS3230.json");
        } catch (IOException e) {
            ;
        }

        // bad path -> throws NullPointerException
        //assertThrows(NullPointerException.class,
        //        () -> CourseUtil.getCourseJsonString("invalidPath", "invalidFile.json"));
    }

    @Test
    public void getCoursePrefixTest() {
        assertEquals("MA", CourseUtil.getCoursePrefix("MA1511"));
        assertEquals("CS", CourseUtil.getCoursePrefix("CS1231"));
        assertEquals("AUD", CourseUtil.getCoursePrefix("AUD4321"));
        assertEquals("", CourseUtil.getCoursePrefix("2AUD32"));
        assertDoesNotThrow(() -> CourseUtil.getCoursePrefix("123"));
        assertDoesNotThrow(() -> CourseUtil.getCoursePrefix(""));
    }
}
