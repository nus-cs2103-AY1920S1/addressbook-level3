package seedu.jarvis.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static seedu.jarvis.testutil.course.TypicalCourses.CS1010;
import static seedu.jarvis.testutil.course.TypicalCourses.CS3230;
import static seedu.jarvis.testutil.course.TypicalCourses.MA1521;

import org.junit.jupiter.api.Test;

class CourseDisplayTextTest {

    @Test
    public void equals_sameObjects_returnsTrue() {
        CourseDisplayText cdt1 = new CourseDisplayText();
        cdt1.setValue(MA1521);
        CourseDisplayText cdt2 = new CourseDisplayText();
        cdt2.setValue(MA1521);
        assertEquals(cdt1, cdt2);
    }

    @Test
    public void equals_differentObjects_returnsFalse() {
        CourseDisplayText cdt1 = new CourseDisplayText();
        cdt1.setValue(MA1521);
        CourseDisplayText cdt2 = new CourseDisplayText();
        cdt2.setValue(CS1010);
        assertNotEquals(cdt1, cdt2);
    }

    @Test
    public void setValue_sameCourse_same() {
        CourseDisplayText cdt = new CourseDisplayText();
        cdt.setValue(CS3230);
        assertEquals(cdt.getAsString(), CS3230.toDisplayableString());
    }
}
