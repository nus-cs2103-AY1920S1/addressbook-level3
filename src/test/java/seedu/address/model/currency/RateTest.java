package seedu.address.model.currency;

import org.junit.jupiter.api.Test;
import seedu.address.model.itinerary.Location;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class RateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Rate(null));
    }

    @Test
    public void constructor_invalidRate_throwsIllegalArgumentException() {
        String invalidRate = "";
        assertThrows(IllegalArgumentException.class, () -> new Rate(invalidRate));
    }

    @Test
    public void isValid() {
        assertFalse(Rate.isValidRate("")); // empty string
        assertFalse(Rate.isValidRate(" ")); // spaces only
        assertFalse(Rate.isValidRate("0.1234")); // containing more than 3 decimal place
        assertFalse(Rate.isValidRate("-0.12")); //  negative double value
        assertFalse(Rate.isValidRate("Not a number")); // containing only alphabets

        // valid addresses
        assertTrue(Rate.isValidRate("0")); // no decimal point
        assertTrue(Location.isValidLocation("0.5")); // one decimal point
        assertTrue(Location.isValidLocation("4.99")); // two decimal points
    }

}
