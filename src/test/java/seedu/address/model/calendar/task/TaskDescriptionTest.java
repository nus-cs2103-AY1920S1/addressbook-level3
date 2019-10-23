package seedu.address.model.calendar.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class TaskDescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskDescription(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new TaskDescription(invalidDescription));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> TaskDescription.isValidDescription(null));

        // blank email
        assertFalse(TaskDescription.isValidDescription("")); // empty string
        assertFalse(TaskDescription.isValidDescription(" ")); // spaces only

        // valid email
        assertTrue(TaskDescription.isValidDescription("This is a valid description"));
        assertTrue(TaskDescription.isValidDescription("1234137964"));
        assertTrue(TaskDescription.isValidDescription("afdjhan")); // alphabets only
        assertTrue(TaskDescription
                .isValidDescription("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(TaskDescription
                .isValidDescription("a1+be!@example1.com")); // mixture of alphanumeric and special characters
    }
}
