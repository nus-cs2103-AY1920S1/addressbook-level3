package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalidCategory_throwsIllegalArgumentException() {
        String invalidLocation = "";
        assertThrows(IllegalArgumentException.class, () -> new Location(invalidLocation));
    }

    @Test
    public void isEmptyLocation() {
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only
        assertFalse(Location.isValidLocation("             ")); // tons of spaces
    }

    @Test
    public void isNullLocation() {
        // null location
        assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));
    }

    @Test
    public void isValidLocation() {
        // valid location
        assertTrue(Location.isValidLocation("abc")); // short string
        assertTrue(Location.isValidLocation("Abracadabra")); // medium string
        assertTrue(Location.isValidLocation("Abracadabra Alakazam")); // long string

        // with unexpected characters
        assertTrue(Location.isValidLocation("@35^"));
    }

    @Test
    public void get_field_test() {
        String sampleString = "123321";
        assertEquals(new Location(sampleString).getField(), sampleString);
        assertNotEquals(new Location(sampleString).getField(), "");
    }

    @Test
    public void compareTests() {
        Location normalLocation = new Location("888888");
        assertEquals(normalLocation.compareTo(null), 1);
        assertEquals(normalLocation.compareTo(normalLocation), 0);
    }
}
