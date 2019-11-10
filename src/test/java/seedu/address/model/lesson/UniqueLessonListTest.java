package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.LESSON_ONE;
import static seedu.address.testutil.TypicalLessons.LESSON_TWO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;

public class UniqueLessonListTest {
    private final UniqueLessonList uniqueLessonList = new UniqueLessonList();

    @Test
    public void contains_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.contains(null));
    }

    @Test
    public void contains_assignmentNotInList_returnsFalse() {
        assertFalse(uniqueLessonList.contains(LESSON_ONE));
    }

    @Test
    public void contains_assignmentInList_returnsTrue() {
        uniqueLessonList.add(LESSON_ONE);
        assertTrue(uniqueLessonList.contains(LESSON_ONE));
    }

    @Test
    public void add_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.add(null));
    }

    @Test
    public void add_duplicateLesson_throwsDuplicateLessonException() {
        uniqueLessonList.add(LESSON_ONE);
        assertThrows(seedu.address.model.lesson.exceptions.DuplicateLessonException.class,
                () -> uniqueLessonList.add(LESSON_ONE));
    }

    @Test
    public void setLesson_nullTargetLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueLessonList.setLesson(null, LESSON_ONE));
    }

    @Test
    public void setLesson_nullEditedLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueLessonList.setLesson(LESSON_ONE, null));
    }

    @Test
    public void setLesson_targetLessonNotInList_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () ->
                uniqueLessonList.setLesson(LESSON_ONE, LESSON_ONE));
    }

    @Test
    public void setLesson_editedLessonIsSameLesson_success() {
        uniqueLessonList.add(LESSON_ONE);
        uniqueLessonList.setLesson(LESSON_ONE, LESSON_ONE);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(LESSON_ONE);
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLesson_editedLessonHasDifferentIdentity_success() {
        uniqueLessonList.add(LESSON_ONE);
        uniqueLessonList.setLesson(LESSON_ONE, LESSON_TWO);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(LESSON_TWO);
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void remove_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.remove(null));
    }

    @Test
    public void remove_assignmentDoesNotExist_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> uniqueLessonList.remove(LESSON_ONE));
    }

    @Test
    public void remove_existingLesson_removesLesson() {
        uniqueLessonList.add(LESSON_ONE);
        uniqueLessonList.remove(LESSON_ONE);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLessons_nullUniqueLessonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueLessonList.setLessons((UniqueLessonList) null));
    }

    @Test
    public void setLessons_uniqueLessonList_replacesOwnListWithProvidedUniqueLessonList() {
        uniqueLessonList.add(LESSON_ONE);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(LESSON_TWO);
        uniqueLessonList.setLessons(expectedUniqueLessonList);
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLessons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLessons((List<Lesson>) null));
    }

    @Test
    public void setLessons_list_replacesOwnListWithProvidedList() {
        uniqueLessonList.add(LESSON_ONE);
        List<Lesson> assignmentList = Collections.singletonList(LESSON_TWO);
        uniqueLessonList.setLessons(assignmentList);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(LESSON_TWO);
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLessons_listWithDuplicateLessons_throwsDuplicateLessonException() {
        List<Lesson> listWithDuplicateLessons = Arrays.asList(LESSON_ONE, LESSON_ONE);
        assertThrows(
                DuplicateLessonException.class, () ->
                        uniqueLessonList.setLessons(listWithDuplicateLessons));
    }
    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueLessonList.asUnmodifiableObservableList().remove(0));
    }

}
