package seedu.address.model.deadline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineTest {

    private static final String VALID_TASK_STR = "Complete Deadline Scheduler.";
    private static final String VALID_TASK_STR_2 = "Complete Calendar GUI.";
    private static final String VALID_DUEDATE_STR = "01/10/2019";
    private static final String VALID_DUEDATE_STR_2 = "11/10/2019";
    private static final Task VALID_TASK = new Task(VALID_TASK_STR);
    private static final DueDate VALID_DUEDATE = new DueDate(VALID_DUEDATE_STR);
    private static final Task VALID_TASK_2 = new Task(VALID_TASK_STR_2);
    private static final DueDate VALID_DUEDATE_2 = new DueDate(VALID_DUEDATE_STR_2);
    public static Deadline DEADLINE = new Deadline(VALID_TASK, VALID_DUEDATE);


    @Test
    public void isSameDeadline() {
        // same object -> returns true
        assertTrue(DEADLINE.isSameDeadline(DEADLINE));


        // null -> returns false
        assertFalse(DEADLINE.isSameDeadline(null));

        // same deadline -> returns true
        Deadline editedDeadline = new Deadline(VALID_TASK, VALID_DUEDATE);
        assertTrue(DEADLINE.isSameDeadline(editedDeadline));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Deadline deadline = new Deadline(VALID_TASK, VALID_DUEDATE);
        assertTrue(DEADLINE.equals(deadline));

        // same object -> returns true
        assertTrue(DEADLINE.equals(DEADLINE));

        // null -> returns false
        assertFalse(DEADLINE.equals(null));

        // different type -> returns false
        assertFalse(DEADLINE.equals(5));

        // different deadline -> returns false
        // Revisit!
        //Deadline otherDeadline = new Deadline(VALID_TASK_2, VALID_DUEDATE_2);
        //assertFalse(deadline.toString().equals(otherDeadline.toString()));

        // different task -> returns false
        Deadline editedDeadlineTask = new Deadline(VALID_TASK_2, VALID_DUEDATE_2);
        assertNotEquals(DEADLINE.toString(), editedDeadlineTask.toString());

        // different due date -> returns false
        Deadline editedDeadlineDueDate = new Deadline(VALID_TASK, VALID_DUEDATE);
        editedDeadlineDueDate.setDueDate(new DueDate(VALID_DUEDATE_STR_2));
        assertNotEquals(DEADLINE.toString(), editedDeadlineDueDate.toString());

    }
}
