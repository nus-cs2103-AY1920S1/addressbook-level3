package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

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
        assertTrue(OpeningHours.isValidOpeningHours("1200 1900"));
        assertTrue(OpeningHours.isValidOpeningHours("0000 2359"));
        assertTrue(OpeningHours.isValidOpeningHours("1200 1200"));
    }

    @Test
    public void get_field_test() {
        String sampleString = "0000 2000";
        assertEquals(new OpeningHours(sampleString).getField(), sampleString);
        assertNotEquals(new OpeningHours(sampleString).getField(), "");
    }

    @Test
    public void compareTests() {
        OpeningHours normalOpeningHours = new OpeningHours("0000 2359");
        assertEquals(normalOpeningHours.compareTo(null), 1);
        assertEquals(normalOpeningHours.compareTo(normalOpeningHours), 0);
    }
}
