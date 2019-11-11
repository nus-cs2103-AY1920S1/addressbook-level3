package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VenueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Venue(null));
    }

    @Test
    public void constructor_invalidVenue_throwsIllegalArgumentException() {
        String invalidVenue = "";
        assertThrows(IllegalArgumentException.class, () -> new Venue(invalidVenue));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Venue.isValidVenue(null));

        // invalid addresses
        assertFalse(Venue.isValidVenue("")); // empty string
        assertFalse(Venue.isValidVenue(" ")); // spaces only

        // valid addresses
        assertTrue(Venue.isValidVenue("Clementi MRT station")); // alphabets only
        assertTrue(Venue.isValidVenue("S123123")); // numbers and alphabets
        assertTrue(Venue.isValidVenue("523523")); // numbers only
        assertTrue(Venue.isValidVenue("Vivo City Basement 1 beside Gongcha at Harbourfront MRT")); // long venue
    }
}
