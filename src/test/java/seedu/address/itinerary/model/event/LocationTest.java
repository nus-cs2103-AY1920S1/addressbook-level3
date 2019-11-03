package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void isValidLocation() {
        // invalid location
        assertFalse(Location.isValidLocation(
                "000000000000000000000")); // Exceed 20 character limit

        // valid location
        assertTrue(Location.isValidLocation("Valid location")); // Valid location
        assertTrue(Location.isValidLocation("")); // Empty description
        assertTrue(Location.isValidLocation("Singapore")); // Less than 20 characters
        assertTrue(Location.isValidLocation("Θ θ, Ι ι, Κ κ, Λ λ ο")); // Greek
    }

    @Test
    void testToString() {
        Location location = new Location("Singapore");
        Location location2 = new Location("");
        assertEquals(location.toString(), "Singapore");
        assertEquals(location2.toString(), "");
    }
}
