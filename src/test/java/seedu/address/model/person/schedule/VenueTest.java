package seedu.address.model.person.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.VENUE1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.VENUE2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VenueTest {

    private Venue venue;

    @BeforeEach
    void init() {
        venue = VENUE1;
    }

    @Test
    void testEquals() {
        assertTrue(VENUE1.equals(VENUE1));
        assertFalse(VENUE1.equals(VENUE2));
    }

    @Test
    void equals_null() {
        assertFalse(venue.equals(null));
    }

    @Test
    void equals_notVenue() {
        assertFalse(venue.equals(TIME_SLOT1));
    }

    @Test
    void getVenue() {
        assertEquals(VENUE1.toString(), VENUE1.getVenue());
    }

    @Test
    void setVenue() {
        venue.setVenue(VENUE2.toString());
        assertTrue(venue.equals(VENUE2));
    }
}
