package seedu.jarvis.storage.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.course.TypicalCourses.CS1010;
import static seedu.jarvis.testutil.course.TypicalCourses.CS3230;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.course.Course;

/**
 * Tests the behaviour of {@code JsonAdaptedCourse}.
 */
public class JsonAdaptedCourseTest {
    private static final Course COURSE_ALL_ATTRIBUTES = CS3230;
    private static final Course COURSE_NULLABLE_ATTRIBUTES_NULL = CS1010;

    @Test
    public void toModelType_allAttributesNonNull_returnsCourse() throws Exception {
        JsonAdaptedCourse jsonAdaptedCourse = new JsonAdaptedCourse(COURSE_ALL_ATTRIBUTES);
        assertEquals(COURSE_ALL_ATTRIBUTES, jsonAdaptedCourse.toModelType());
    }

    @Test
    public void toModelType_nullableAttributesNull_returnsCourse() throws Exception {
        JsonAdaptedCourse jsonAdaptedCourse = new JsonAdaptedCourse(COURSE_NULLABLE_ATTRIBUTES_NULL);
        assertEquals(COURSE_NULLABLE_ATTRIBUTES_NULL, jsonAdaptedCourse.toModelType());
    }

}
