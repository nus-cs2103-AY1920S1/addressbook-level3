package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.model.util.Time;

public class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null Time
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid Time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("abc")); // alphabet only
        assertFalse(Time.isValidTime("^!")); // only non-alphanumeric characters
        assertFalse(Time.isValidTime("1921*")); // contains non-alphanumeric characters
        assertFalse(Time.isValidTime("191")); // not in correct format
        assertFalse(Time.isValidTime("6969")); // not a valid time

        // valid Time
        assertTrue(Time.isValidTime("0000")); // numerical digits only
        assertTrue(Time.isValidTime("2359"));
    }

    @Test
    public void equals() {
        Time firstTime = new Time("2119");
        Time secondTime = new Time("1921");

        // same object -> returns true
        assertTrue(firstTime.equals(firstTime));

        // same values -> returns true
        assertTrue(firstTime.equals(new Time("2119")));

        // different types -> returns false
        assertFalse(firstTime.equals(1));

        // null -> returns false
        assertFalse(firstTime.equals(null));

        // different time -> returns false
        assertFalse(firstTime.equals(secondTime));
    }

}
