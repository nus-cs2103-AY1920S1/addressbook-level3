package seedu.address.model.deadline;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class DueDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DueDate(null));
    }

    @Test
    public void constructor_invalidDueDate_throwsIllegalArgumentException() {
        String invalidDueDate = "";
        assertThrows(DateTimeParseException.class, () -> new DueDate(invalidDueDate));
    }

    @Test
    public void isValidDueDate() {
        // null date
        assertThrows(NullPointerException.class, () -> DueDate.isValidDate(null));

        // invalid date
        assertFalse(DueDate.isValidDate("")); // empty string
        assertFalse(DueDate.isValidDate(" ")); // spaces only
        assertFalse(DueDate.isValidDate("40/10/2019")); // day element is invalid
        assertFalse(DueDate.isValidDate("10/40/2019")); // month element is invalid

        // valid date
        assertTrue(Task.isValidTask("10/10/2019")); // a valid date
    }
}
