package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_TUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_TUE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.MON;
import static seedu.address.testutil.TypicalLessons.ANOTHER_MON;
import static seedu.address.testutil.TypicalLessons.TUE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LessonBuilder;

public class LessonTest {

    @Test
    public void isSameDay() {
        //same object -> returns true
        assertTrue(MON.isSameDay(MON));

        //same day -> returns true;
        assertTrue(MON.isSameDay(ANOTHER_MON));

        //different day -> returns false
        assertFalse(MON.isSameDay(TUE));

        //edited to different day -> returns false
        Lesson editedMon = new LessonBuilder(MON).withStartTime(VALID_STARTTIME_TUE).withEndTime(VALID_ENDTIME_TUE)
                .build();
        assertFalse(MON.isSameDay(editedMon));
    }

    @Test
    public void isSameLesson() {
        //same object -> returns true
        assertTrue(MON.isSameLesson(MON));

        //null object -> returns false
        assertFalse(MON.isSameLesson(null));

        //different day and class name -> returns false
        assertFalse(MON.isSameLesson(TUE));

        //edited to have same class name, start time, end time -> returns true
        Lesson editedTue = new LessonBuilder(TUE).withClassName("Monday Class").withStartTime(VALID_STARTTIME_MON)
                .withEndTime(VALID_ENDTIME_MON).build();
        assertTrue(MON.isSameLesson(editedTue));

        //edited to only have same class name -> returns false
        editedTue = new LessonBuilder(TUE).withClassName("Monday Class").build();
        assertFalse(MON.isSameLesson(editedTue));
    }

    @Test
    public void equals() {
        //same object -> returns true
        assertTrue(MON.equals(MON));

        //null object -> returns false
        assertFalse(MON.equals(null));

        //different day and class name -> returns false
        assertFalse(MON.equals(TUE));

        //edited to have same class name, start time, end time -> returns true
        Lesson editedTue = new LessonBuilder(TUE).withClassName("Monday Class").withStartTime(VALID_STARTTIME_MON)
                .withEndTime(VALID_ENDTIME_MON).build();
        assertTrue(MON.equals(editedTue));

        //edited to only have same class name -> returns false
        editedTue = new LessonBuilder(TUE).withClassName("Monday Class").build();
        assertFalse(MON.equals(editedTue));

        //different object -> returns false
        assertFalse(MON.equals("HI"));

        //different object -> returns false
        assertFalse(TUE.equals("Bye"));
    }
}
