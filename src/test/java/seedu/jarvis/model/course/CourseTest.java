package seedu.jarvis.model.course;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.jarvis.testutil.course.TypicalCourses.*;

public class CourseTest {
    @Test
    public void equals_sameCourse_returnsTrue() {
        assertEquals(CS3230, CS3230);
        assertEquals(CS1010, CS1010);
        assertEquals(MA1521, MA1521);
    }

    @Test
    public void equals_differentCourse_returnsFalse() {
        assertNotEquals(CS3230, CS1010);
        assertNotEquals(CS1010, MA1521);
        assertNotEquals(MA1521, CS3230);
    }
}
