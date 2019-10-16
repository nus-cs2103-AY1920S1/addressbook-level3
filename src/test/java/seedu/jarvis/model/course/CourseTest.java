package seedu.jarvis.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.jarvis.testutil.course.TypicalCourses;

public class CourseTest {
    @Test
    public void equals_sameCourse_returnsTrue() {
        assertEquals(TypicalCourses.CS3230, TypicalCourses.CS3230);
        assertEquals(TypicalCourses.CS1010, TypicalCourses.CS1010);
        assertEquals(TypicalCourses.MA1521, TypicalCourses.MA1521);
    }

    @Test
    public void equals_differentCourse_returnsFalse() {
        assertNotEquals(TypicalCourses.CS3230, TypicalCourses.CS1010);
        assertNotEquals(TypicalCourses.CS1010, TypicalCourses.MA1521);
        assertNotEquals(TypicalCourses.MA1521, TypicalCourses.CS3230);
    }

    @Test
    public void toDisplayableString_exampleCourse_returnsCorrectString() {
    }
}
