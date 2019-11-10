package seedu.address.model.vehicle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AvailabilityTest {
    Availability a1 = new Availability(Availability.VEHICLE_AVAILABLE_TAG);
    Availability a2 = new Availability(Availability.VEHICLE_AVAILABLE_TAG);
    Availability a3 = new Availability(Availability.VEHICLE_BUSY_TAG);

    @Test
    public void equals() {
        // same object -> returns true
        assertEquals(a1, a1);

        // same tag -> returns true
        assertEquals(a1, a2);

        // diff tags -> returns false
        assertFalse(a1.equals(a3));
    }

    @Test
    public void isValidAvailability_validTag_returnsTrue() {
        assertTrue(a1.isValidAvailability(Availability.VEHICLE_AVAILABLE_TAG));
    }

    @Test
    public void isValidAvailability_invalidTag_returnsFalse() {
        assertFalse(a1.isValidAvailability("bus"));
    }
}
