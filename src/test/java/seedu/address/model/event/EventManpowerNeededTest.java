package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EventManpowerNeededTest {

    @Test
    void isValidEventManpowerNeeded() {
        // invalid Event Manpower Needed
        assertFalse(EventManpowerNeeded.isValidEventManpowerNeeded("50 people")); // Invalid input
        assertFalse(EventManpowerNeeded.isValidEventManpowerNeeded("50000")); // exceed limit
        assertFalse(EventManpowerNeeded.isValidEventManpowerNeeded("1000")); // exceed limit

        // valid Event Manpower Needed
        assertTrue(EventHoursNeeded.isValidEventHours("000"));
        assertTrue(EventHoursNeeded.isValidEventHours("005"));
        assertTrue(EventHoursNeeded.isValidEventHours("035"));
        assertTrue(EventHoursNeeded.isValidEventHours("999"));
    }

    @Test
    void eventManpowerNeededEquals() {
        assertNotEquals(new EventManpowerNeeded("20"), new EventManpowerNeeded("21"));
        assertEquals(new EventManpowerNeeded("50"), new EventManpowerNeeded("50"));
    }
}
