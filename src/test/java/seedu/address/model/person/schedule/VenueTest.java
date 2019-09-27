package seedu.address.model.person.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.VENUE1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.VENUE2;

import org.junit.jupiter.api.Test;

class VenueTest {

    @Test
    void testEquals() {
        assertTrue(VENUE1.equals(VENUE1));
        assertFalse(VENUE1.equals(VENUE2));
    }

    @Test
    void getVenue() {
        assertEquals(VENUE1.toString(), VENUE1.getVenue());
        assertNotEquals(VENUE2.toString(), VENUE1.getVenue());
    }
}
