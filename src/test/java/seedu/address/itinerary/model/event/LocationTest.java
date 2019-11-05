package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void isValidLocation() {
        // invalid location
        assertFalse(Location.isValidLocation(
                "000000000000000000000")); // Exceed 20 character limit at 21 characters

        // valid location
        assertTrue(Location.isValidLocation("Valid location")); // Valid location

        assertTrue(Location.isValidLocation("")); // Empty description

        assertTrue(Location.isValidLocation("Singapore")); // Less than 20 characters

        assertTrue(Location.isValidLocation("Θ θ, Ι ι, Κ κ, Λ λ ο")); // Greek

        assertTrue(Location.isValidLocation(
                "00000000000000000000")); // Exactly 20 characters
    }

    @Test
    void testToString() {
        Location location = new Location("Singapore");
        Location location2 = new Location("");
        Location location3 = new Location("00000000000000000000");

        // Testing different expected values
        // Empty location
        assertNotEquals("", location);

        // Only valid location with be created for string that pass the location validation test.
        assertEquals("Singapore", location.toString());

        assertEquals("", location2.toString());

        assertEquals("00000000000000000000", location3.toString());
    }
}
