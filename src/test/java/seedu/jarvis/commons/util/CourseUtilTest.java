package seedu.jarvis.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * @author ryanYtan
 */
public class CourseUtilTest {
    public static final String[] VALID_COURSE_CODES = {
        "MA1511",
        "CS3230",
        "CS1101S",
        "ST2334",
        "ALS1020"
    };

    public static final String[] INVALID_COURSE_CODES = {
        "MA1234",
        "ESP1107",
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

    @Test
    public void getCourse_validInput_success() {
        for (String course : VALID_COURSE_CODES) {
            assertTrue(CourseUtil.getCourse(course).isPresent());
        }
    }

    @Test
    public void getCourse_invalidInput_returnsOptionalEmpty() {
        for (String course : INVALID_COURSE_CODES) {
            assertEquals(CourseUtil.getCourse(course), Optional.empty());
        }
    }

    @Test
    public void courseExists_validInput_returnsTrue() {
        for (String course : VALID_COURSE_CODES) {
            assertTrue(CourseUtil.courseExists(course));
        }
    }

    @Test
    public void courseExists_invalidInput_returnsFalse() {
        for (String course : INVALID_COURSE_CODES) {
            assertFalse(CourseUtil.courseExists(course));
        }
    }

}
