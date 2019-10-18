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
        assertTrue(EventManpowerNeeded.isValidEventManpowerNeeded("000"));
        assertTrue(EventManpowerNeeded.isValidEventManpowerNeeded("005"));
        assertTrue(EventManpowerNeeded.isValidEventManpowerNeeded("035"));
        assertTrue(EventManpowerNeeded.isValidEventManpowerNeeded("999"));
    }

    @Test
    void eventManpowerNeededEquals() {
        assertNotEquals(new EventManpowerNeeded("20"), new EventManpowerNeeded("21"));
        assertEquals(new EventManpowerNeeded("50"), new EventManpowerNeeded("50"));
    }
}
