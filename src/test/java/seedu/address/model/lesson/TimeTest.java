package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void isValidTime() {
        // null name
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("^")); // only non-alphanumeric characters
        assertFalse(Time.isValidTime("1234/5678/1010")); // incorrect format of time
        assertFalse(Time.isValidTime("12/02/2010 250")); // incorrect time

        // valid time
        assertTrue(Time.isValidTime("12/02/2020 1200"));
        assertTrue(Time.isValidTime("23/09/2020 1000"));
        assertTrue(Time.isValidTime("14/02/2020 0000"));
    }
}
