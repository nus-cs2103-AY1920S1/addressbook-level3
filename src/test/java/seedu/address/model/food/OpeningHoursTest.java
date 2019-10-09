package seedu.address.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OpeningHoursTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OpeningHours(null));
    }

    @Test
    public void constructor_invalidOpeningHours_throwsIllegalArgumentException() {
        String invalidOpeningHours = "";
        assertThrows(IllegalArgumentException.class, () -> new OpeningHours(invalidOpeningHours));
    }

    @Test
    public void isInvalidOpeningHours() {

        // blank opening hours
        assertFalse(OpeningHours.isValidOpeningHours("")); // empty string
        assertFalse(OpeningHours.isValidOpeningHours(" ")); // spaces only

        // with unexpected characters
        assertFalse(OpeningHours.isValidOpeningHours("@35^"));

        // wrong HHMM HHMM format
        assertFalse(OpeningHours.isValidOpeningHours("0823 20923"));
    }

    @Test
    public void isValidOpeningHours() {
        // valid opening hours
        assertTrue(OpeningHours.isValidOpeningHours("1200 1900")); // Correct HHMM HHMM format.
    }
}
