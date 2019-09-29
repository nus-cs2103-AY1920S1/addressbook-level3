package seedu.jarvis.commons.util;

import org.junit.jupiter.api.Test;
import seedu.jarvis.model.course.Course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.util.Map;

public class CourseUtilTest {
    @Test
    public void getCourseJsonString_invalidPath_throwsIOException() {
        assertThrows(IOException.class,
                () -> CourseUtil.getCourseJsonString("invalid.json"));
        assertThrows(IOException.class,
                () -> CourseUtil.getCourseJsonString(""));
        assertThrows(IOException.class,
                () -> CourseUtil.getCourseJsonString("CS32333"));
    }

    @Test
    public void getCourseJsonString_validPath_doesNotThrowException() {
        assertDoesNotThrow(() -> CourseUtil.getCourseJsonString("MA1511"));
        assertDoesNotThrow(() -> CourseUtil.getCourseJsonString("CS3230"));
    }

    @Test
    public void getJsonMap__success() {
        try {
            Map<String, String> jsonMap = CourseUtil.getCourseMap("CS3230");
            jsonMap.forEach((k, v) -> System.out.println(k + ", " + v));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getCourse_validInput_success() {
        String[] toTest = {
           "MA1511",
           "CS3230",
           "ESP1107",
           "CS1101S",
           "ST2334",
           "ALS1020"
        };
        for (String course : toTest) {
            assertDoesNotThrow(() -> CourseUtil.getCourse(course));
        }
    }

    @Test
    public void getCourse_invalidInput_throwsIOException() {
        String[] toTest = {
                "MA1234",
                "",
                "JSKD23424",
                "wekjfhqweikfh",
                "garbage values",
                "CS9999",
                "CS50123",
                "MA15101",
                "MA1511-1",
                "MA1511-2",
                "\\asd'\\sd;",
                "???",
        };

        for (String course : toTest) {
            assertThrows(IOException.class, () -> CourseUtil.getCourse(course));
        }
    }
}
