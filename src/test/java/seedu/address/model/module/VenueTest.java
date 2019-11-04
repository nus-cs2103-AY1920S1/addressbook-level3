package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class VenueTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Venue(null));
    }

    @Test
    void testEquals() {
        Venue venue = new Venue("COM1");

        // same values -> return true
        assertTrue(venue.equals(new Venue("COM1")));

        // same object -> return true
        assertTrue(venue.equals(venue));

        // null -> returns false
        assertFalse(venue.equals(null));

        // different venue -> returns false
        assertFalse(venue.equals(new Venue("COM2")));
    }

    @Test
    void testHashCode() {
        Venue v1 = new Venue("COM1");
        Venue v2 = new Venue("COM1");
        Venue v3 = new Venue("COM2");
        assertEquals(v1.hashCode(), v1.hashCode());
        assertEquals(v1.hashCode(), v2.hashCode());
        assertNotEquals(v1.hashCode(), v3.hashCode());
    }
}
