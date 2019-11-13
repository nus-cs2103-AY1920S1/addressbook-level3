package seedu.planner.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriorityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        Integer invalidPriority = -1;
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {

        // null priority
        assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // invalid value
        assertFalse(Priority.isValidPriority("-1")); // invalid negative value
        assertFalse(Priority.isValidPriority("1.4")); // decimal value
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("thirty")); // non-numeric
        assertFalse(Priority.isValidPriority("PRIORITY3")); // alphabets within digits

        // valid priority
        assertTrue(Priority.isValidPriority("1")); //min priority
        assertTrue(Priority.isValidPriority("7")); // max value

    }
}
