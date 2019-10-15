package seedu.jarvis.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.course.TypicalCourses.CS1010;
import static seedu.jarvis.testutil.course.TypicalCourses.CS3230;
import static seedu.jarvis.testutil.course.TypicalCourses.MA1521;

import org.junit.jupiter.api.Test;

public class CoursePlannerTest {
    @Test
    public void equals_sameCourses_returnsTrue() {
        UniqueCourseList uniqueCourseList1 = new UniqueCourseList();
        uniqueCourseList1.add(CS1010);
        uniqueCourseList1.add(CS3230);

        UniqueCourseList uniqueCourseList2 = new UniqueCourseList();
        uniqueCourseList2.add(CS1010);
        uniqueCourseList2.add(CS3230);

        assertEquals(uniqueCourseList1, uniqueCourseList2);
    }
}

