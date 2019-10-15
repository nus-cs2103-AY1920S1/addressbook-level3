package seedu.jarvis.model.course;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.jarvis.testutil.course.TypicalCourses.*;

import org.junit.jupiter.api.Test;
import seedu.jarvis.model.course.exceptions.CourseAlreadyInListException;
import seedu.jarvis.model.course.exceptions.CourseNotInListException;


public class UniqueCourseListTest {
    private final UniqueCourseList uniqueCourseList = new UniqueCourseList();

    @Test
    public void contains_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCourseList.contains(null));
    }

    @Test
    public void contains_courseNotInList_returnsFalse() {
        assertFalse(uniqueCourseList.contains(CS3230));
    }

    @Test
    public void contains_courseInList_returnsTrue() {
        uniqueCourseList.add(CS3230);
        assertTrue(uniqueCourseList.contains(CS3230));
    }

    @Test
    public void add_newCourse() {
        uniqueCourseList.add(CS1010);
        uniqueCourseList.contains(CS1010);
    }

    @Test
    public void add_indexOutOfBounds_throwsException() {
        uniqueCourseList.add(CS1010);
        uniqueCourseList.add(MA1521);
        int oobOver = 3;
        int oobUnder = -1;
        assertThrows(IndexOutOfBoundsException.class, () -> uniqueCourseList.add(oobOver, CS3230));
        assertThrows(IndexOutOfBoundsException.class, () -> uniqueCourseList.add(oobUnder, CS3230));
    }

    @Test
    public void add_duplicateCourse_throwsException() {
        uniqueCourseList.add(CS1010);
        assertThrows(CourseAlreadyInListException.class, () -> uniqueCourseList.add(CS1010));
    }

    @Test
    public void remove_courseNotInList_throwsException() {
        assertThrows(CourseNotInListException.class, () -> uniqueCourseList.remove(CS3230));
        uniqueCourseList.add(CS1010);
        assertThrows(CourseNotInListException.class, () -> uniqueCourseList.remove(CS3230));
    }

    @Test
    public void remove_courseInList_success() {
        uniqueCourseList.add(CS1010);
        assertDoesNotThrow(() -> uniqueCourseList.remove(CS1010));
    }

    @Test
    public void get_indexOutOfBounds_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> uniqueCourseList.get(0));
        uniqueCourseList.add(CS1010);
        int oobUnder = -1;
        int oobOver = 1;
        assertThrows(IndexOutOfBoundsException.class, () -> uniqueCourseList.get(oobUnder));
        assertThrows(IndexOutOfBoundsException.class, () -> uniqueCourseList.get(oobOver));
    }
}
