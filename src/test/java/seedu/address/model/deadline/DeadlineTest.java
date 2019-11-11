package seedu.address.model.deadline;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.TypicalDeadlines.COMPLETE_CALENDAR;
import static seedu.address.testutil.TypicalDeadlines.COMPLETE_SCHEDULER;
import static seedu.address.testutil.TypicalDeadlines.VALID_DUEDATE_STR;
import static seedu.address.testutil.TypicalDeadlines.VALID_TASK_STR;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeadlineBuilder;

public class DeadlineTest {

    @Test
    public void isSameDeadline() {
        // same object -> returns true
        assertTrue(COMPLETE_SCHEDULER.equals(COMPLETE_SCHEDULER));


        // null -> returns false
        assertFalse(COMPLETE_SCHEDULER.equals(null));

        // same deadline -> returns true
        Deadline editedDeadline = new DeadlineBuilder(COMPLETE_SCHEDULER).build();
        assertTrue(COMPLETE_SCHEDULER.equals(editedDeadline));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Deadline editedDeadline = new DeadlineBuilder(COMPLETE_CALENDAR).build();
        assertTrue(COMPLETE_CALENDAR.equals(editedDeadline));

        // same object -> returns true
        assertTrue(COMPLETE_CALENDAR.equals(COMPLETE_CALENDAR));

        // null -> returns false
        assertFalse(COMPLETE_CALENDAR.equals(null));

        // different type -> returns false
        assertFalse(COMPLETE_CALENDAR.equals(5));

        // different deadline -> returns false
        Deadline otherDeadline = new DeadlineBuilder(COMPLETE_SCHEDULER).build();
        assertFalse(COMPLETE_CALENDAR.equals(otherDeadline));

        // different task -> returns false
        Deadline editedDeadlineTask = new DeadlineBuilder(COMPLETE_CALENDAR).withTask(VALID_TASK_STR).build();
        assertNotEquals(COMPLETE_CALENDAR.toString(), editedDeadlineTask.toString());

        // different due date -> returns false
        Deadline editedDeadlineDueDate = new DeadlineBuilder(COMPLETE_CALENDAR).withDueDate(VALID_DUEDATE_STR).build();
        assertNotEquals(COMPLETE_CALENDAR.toString(), editedDeadlineDueDate.toString());

    }
}
