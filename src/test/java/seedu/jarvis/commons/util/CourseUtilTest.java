package seedu.jarvis.commons.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.util.Map;

public class CourseUtilTest {

    @Test
    public void getCourseJsonString_invalidPath_throwsNullPointerException() {
        // invalid path, throws IOException
        assertThrows(IOException.class,
                () -> CourseUtil.getCourseJsonString(
                        "invalidPath", "invalidFile.json"));
    }

    @Test
    public void getCourseJsonString_validPath_returnsFile() {
        // valid path does not throw exception
        assertDoesNotThrow(() -> CourseUtil.getCourseJsonString(
                "MA", "MA1511.json"));
        assertDoesNotThrow(() -> CourseUtil.getCourseJsonString(
                "CS", "CS3230"));
    }

    @Test
    public void getCoursePrefix_courseCodeInputs_success() {
        assertEquals("MA", CourseUtil.getCoursePrefix("MA1511"));
        assertEquals("CS", CourseUtil.getCoursePrefix("CS1231"));
        assertEquals("AUD", CourseUtil.getCoursePrefix("AUD4321"));
        assertEquals("", CourseUtil.getCoursePrefix("2AUD32"));
        assertDoesNotThrow(() -> CourseUtil.getCoursePrefix("123"));
        assertDoesNotThrow(() -> CourseUtil.getCoursePrefix(""));
    }

    @Test
    public void getJsonMap__success() {
        try {
            Map<String, String> jsonMap = CourseUtil.getJsonMap("CS", "CS3230");
            jsonMap.forEach((k, v) -> System.out.println(k + ", " + v));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
