package seedu.jarvis.model.course;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.course.exceptions.CourseNotInListException;
import seedu.jarvis.model.course.exceptions.DuplicateCourseException;
import seedu.jarvis.testutil.course.TypicalCourses;




public class UniqueCourseListTest {
    private final UniqueCourseList uniqueCourseList = new UniqueCourseList();

    @Test
    public void contains_nullCourse_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> uniqueCourseList.contains(null));
    }

    @Test
    public void contains_courseNotInList_returnsFalse() {
        Assertions.assertFalse(uniqueCourseList.contains(TypicalCourses.CS3230));
    }

    @Test
    public void contains_courseInList_returnsTrue() {
        uniqueCourseList.add(TypicalCourses.CS3230);
        Assertions.assertTrue(uniqueCourseList.contains(TypicalCourses.CS3230));
    }

    @Test
    public void add_newCourse() {
        uniqueCourseList.add(TypicalCourses.CS1010);
        uniqueCourseList.contains(TypicalCourses.CS1010);
    }

    @Test
    public void add_atIndex() {
        int exampleIndex = 1;
        uniqueCourseList.add(TypicalCourses.CS1010);
        uniqueCourseList.add(TypicalCourses.CS3230);
        uniqueCourseList.add(exampleIndex, TypicalCourses.MA1521);
        Assertions.assertEquals(uniqueCourseList.get(exampleIndex), TypicalCourses.MA1521);
    }

    @Test
    public void add_indexOutOfBounds_throwsException() {
        uniqueCourseList.add(TypicalCourses.CS1010);
        uniqueCourseList.add(TypicalCourses.MA1521);
        int oobOver = 3;
        int oobUnder = -1;
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            uniqueCourseList.add(oobOver, TypicalCourses.CS3230);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            uniqueCourseList.add(oobUnder, TypicalCourses.CS3230);
        });
    }

    @Test
    public void add_duplicateCourse_throwsException() {
        uniqueCourseList.add(TypicalCourses.CS1010);
        Assertions.assertThrows(DuplicateCourseException.class, () -> uniqueCourseList.add(TypicalCourses.CS1010));
    }

    @Test
    public void remove_courseNotInList_throwsException() {
        Assertions.assertThrows(CourseNotInListException.class, () -> uniqueCourseList.remove(TypicalCourses.CS3230));
        uniqueCourseList.add(TypicalCourses.CS1010);
        Assertions.assertThrows(CourseNotInListException.class, () -> uniqueCourseList.remove(TypicalCourses.CS3230));
    }

    @Test
    public void remove_courseInList_success() {
        uniqueCourseList.add(TypicalCourses.CS1010);
        Assertions.assertDoesNotThrow(() -> uniqueCourseList.remove(TypicalCourses.CS1010));
    }

    @Test
    public void setCourses() {
        List<Course> courses = TypicalCourses.getTypicalCourses();
        uniqueCourseList.setCourses(courses);
        int i = 0;
        for (Course course : courses) {
            Assertions.assertEquals(course, uniqueCourseList.get(i++));
        }
    }

    @Test
    public void setCourses_duplicateCourses_throwsDuplicateCourseException() {
        List<Course> courses = Arrays.asList(TypicalCourses.MA1521, TypicalCourses.MA1521);
        Assertions.assertThrows(DuplicateCourseException.class, () -> uniqueCourseList.setCourses(courses));
    }

    @Test
    public void size() {
        uniqueCourseList.setCourses(TypicalCourses.getTypicalCourses());
        Assertions.assertEquals(uniqueCourseList.size(), TypicalCourses.getTypicalCourses().size());
    }

    @Test
    public void get_indexOutOfBounds_throwsException() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> uniqueCourseList.get(0));
        uniqueCourseList.add(TypicalCourses.CS1010);
        int oobUnder = -1;
        int oobOver = 1;
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> uniqueCourseList.get(oobUnder));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> uniqueCourseList.get(oobOver));
    }

    @Test
    public void equals_sameCourses_returnsTrue() {
        UniqueCourseList uniqueCourseList1 = new UniqueCourseList();
        uniqueCourseList1.add(TypicalCourses.CS1010);
        uniqueCourseList1.add(TypicalCourses.CS3230);

        UniqueCourseList uniqueCourseList2 = new UniqueCourseList();
        uniqueCourseList2.add(TypicalCourses.CS1010);
        uniqueCourseList2.add(TypicalCourses.CS3230);

        Assertions.assertEquals(uniqueCourseList1, uniqueCourseList2);
    }
}
