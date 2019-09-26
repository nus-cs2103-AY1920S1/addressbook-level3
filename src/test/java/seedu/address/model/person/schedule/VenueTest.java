package seedu.address.model.person.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VenueTest {

    private Venue venue;
    @BeforeEach
    void init(){
        venue = new Venue("venue");
    }

    @Test
    void testEquals() {
        Venue venue2 = new Venue("venue");
        Venue venue3 = new Venue("Venue");

        assertTrue(venue.equals(venue2));
        assertFalse(venue.equals(venue3));
    }

    @Test
    void getVenue() {
        assertEquals("venue", venue.getVenue());
    }
}