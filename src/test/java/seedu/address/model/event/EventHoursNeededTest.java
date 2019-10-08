package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EventHoursNeededTest {

    @Test
    void isValidEventHours() {
        // invalid Event Hours
        assertFalse(EventHoursNeeded.isValidEventHours("50 hours")); // Invalid input
        assertFalse(EventHoursNeeded.isValidEventHours("50000")); // exceed limit
        assertFalse(EventHoursNeeded.isValidEventHours("1000")); // exceed limit

        // valid Event Hours
        assertTrue(EventHoursNeeded.isValidEventHours("000"));
        assertTrue(EventHoursNeeded.isValidEventHours("005"));
        assertTrue(EventHoursNeeded.isValidEventHours("035"));
        assertTrue(EventHoursNeeded.isValidEventHours("999"));
    }

    @Test
    void eventHoursEquals() {
        assertNotEquals(new EventHoursNeeded("20"), new EventHoursNeeded("21"));
        assertEquals(new EventHoursNeeded("50"), new EventHoursNeeded("50"));
    }
}
