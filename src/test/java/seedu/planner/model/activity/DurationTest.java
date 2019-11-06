package seedu.planner.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DurationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Duration(null));
    }

    @Test
    public void constructor_invalidDuration_throwsIllegalArgumentException() {
        Integer invalidDuration = -1;
        assertThrows(IllegalArgumentException.class, () -> new Duration(invalidDuration));
    }

    @Test
    public void isValidDuration() {

        // null duration
        assertThrows(NullPointerException.class, () -> Duration.isValidDuration(null));

        // blank duration
        assertFalse(Duration.isValidDuration("")); // empty string
        assertFalse(Duration.isValidDuration(" ")); // spaces only

        // invalid value
        assertFalse(Duration.isValidDuration("-1")); // invalid negative value
        assertFalse(Duration.isValidDuration("1.4")); // decimal value
        assertFalse(Duration.isValidDuration("")); // empty string
        assertFalse(Duration.isValidDuration(" ")); // spaces only
        assertFalse(Duration.isValidDuration("thirty")); // non-numeric
        assertFalse(Duration.isValidDuration("30min")); // alphabets within digits
        assertFalse(Duration.isValidDuration("1440")); // exceed max duration
        assertFalse(Duration.isValidDuration("0")); // exceed min duration

        // valid duration
        assertTrue(Duration.isValidDuration("1")); //min duration
        assertTrue(Duration.isValidDuration("1439")); // max value

    }
}
