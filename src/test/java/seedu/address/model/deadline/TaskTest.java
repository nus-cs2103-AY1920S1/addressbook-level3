package seedu.address.model.deadline;

import org.junit.jupiter.api.Test;
import seedu.address.model.flashcard.Answer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class TaskTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Task(null));
    }

    @Test
    public void constructor_invalidTask_throwsIllegalArgumentException() {
        String invalidTask = "";
        assertThrows(IllegalArgumentException.class, () -> new Task(invalidTask));
    }

    @Test
    public void isValidTask() {
        // null task
        assertThrows(NullPointerException.class, () -> Task.isValidTask(null));

        // invalid task
        assertFalse(Task.isValidTask("")); // empty string
        assertFalse(Task.isValidTask(" ")); // spaces only

        // valid task
        assertTrue(Task.isValidTask("123")); // an integer
        assertTrue(Task.isValidTask("Homework")); // a word
        assertTrue(Task.isValidTask("Today's Homework for all Modules")); // a sentence
    }
}
