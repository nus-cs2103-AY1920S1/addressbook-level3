package seedu.address.model.calendar.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class TaskDeadlineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskDeadline(null));
    }
    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        String invalidDeadline = "";
        assertThrows(IllegalArgumentException.class, () -> new TaskDeadline(invalidDeadline));
    }

    @Test
    public void isValidDeadline() {
        // null time
        assertThrows(NullPointerException.class, () -> TaskTime.isValidTime(null));

        // invalid addresses
        assertFalse(TaskDeadline.isValidDeadline("")); // empty string
        assertFalse(TaskDeadline.isValidDeadline(" ")); // spaces only
        assertFalse(TaskDeadline.isValidDeadline("12/12/2010"));
        assertFalse(TaskDeadline.isValidDeadline("fakjdmff;,a"));
        assertFalse(TaskDeadline.isValidDeadline("a-20-20"));

        // valid addresses
        assertTrue(TaskDeadline.isValidDeadline("01-01-2019"));
        assertTrue(TaskDeadline.isValidDeadline("01-01-1970"));
        assertTrue(TaskDeadline.isValidDeadline("30-01-2999"));
    }
}
