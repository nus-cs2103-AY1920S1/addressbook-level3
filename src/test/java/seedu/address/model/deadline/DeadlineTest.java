package seedu.address.model.deadline;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    private final String validTaskStr = "Complete Deadline Scheduler.";
    private final String validTaskStr2 = "Complete Calendar GUI.";
    private final String ValidDueDateStr = "01/10/2019";
    private final String ValidDueDateStr2 = "11/10/2019";
    private final Task validTask = new Task(validTaskStr);
    private final DueDate validDueDate = new DueDate(ValidDueDateStr);
    private final Task validTask2 = new Task(validTaskStr2);
    private final DueDate validDueDate2 = new DueDate(ValidDueDateStr2);
    public final Deadline validDeadline = new Deadline(validTask, validDueDate);


    @Test
    public void isSameDeadline() {
        // same object -> returns true
        assertTrue(validDeadline.isSameDeadline(validDeadline));


        // null -> returns false
        assertFalse(validDeadline.isSameDeadline(null));

        // same deadline -> returns true
        Deadline editedDeadline = new Deadline(validTask, validDueDate);
        assertTrue(validDeadline.isSameDeadline(editedDeadline));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Deadline deadline = new Deadline(validTask, validDueDate);
        assertTrue(validDeadline.equals(deadline));

        // same object -> returns true
        assertTrue(validDeadline.equals(validDeadline));

        // null -> returns false
        assertFalse(validDeadline.equals(null));

        // different type -> returns false
        assertFalse(validDeadline.equals(5));

        // different deadline -> returns false
        // Revisit!
        Deadline otherDeadline = new Deadline(validTask2, validDueDate2);
        assertFalse(validDeadline.equals(otherDeadline));

        // different task -> returns false
        Deadline editedDeadlineTask = new Deadline(validTask2, validDueDate2);
        assertNotEquals(validDeadline.toString(), editedDeadlineTask.toString());

        // different due date -> returns false
        Deadline editedDeadlineDueDate = new Deadline(validTask2, validDueDate2);
        assertNotEquals(validDeadline.toString(), editedDeadlineDueDate.toString());

    }
}
