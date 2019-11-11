package seedu.address.model.calendar.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class TaskTitleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskTitle(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        assertThrows(IllegalArgumentException.class, () -> new TaskTitle(invalidTitle));
    }

    @Test
    public void isValidTitle() {
        // null name
        assertThrows(NullPointerException.class, () -> TaskTitle.isValidTitle(null));

        // invalid name
        assertFalse(TaskTitle.isValidTitle("")); // empty string
        assertFalse(TaskTitle.isValidTitle(" ")); // spaces only
        assertFalse(TaskTitle.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(TaskTitle.isValidTitle("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(TaskTitle.isValidTitle("peter jack")); // alphabets only
        assertTrue(TaskTitle.isValidTitle("12345")); // numbers only
        assertTrue(TaskTitle.isValidTitle("peter the 2nd")); // alphanumeric characters
        assertTrue(TaskTitle.isValidTitle("Capital Tan")); // with capital letters
        assertTrue(TaskTitle.isValidTitle("David Roger Jackson Ray Jr 2nd")); // long names
    }

}
