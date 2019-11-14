package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class EventVenueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventVenue(null));
    }

    @Test
    public void constructor_invalidEventName_throwsIllegalArgumentException() {
        String invalidEventVenue = "";
        assertThrows(IllegalArgumentException.class, () -> new EventVenue(invalidEventVenue));
    }

    @Test
    void isValidVenue() {
        //null event venue
        assertThrows(NullPointerException.class, () -> EventVenue.isValidVenue(null));

        // invalid event venue
        assertFalse(EventVenue.isValidVenue("")); // empty string
        assertFalse(EventVenue.isValidVenue(" ")); // spaces only

        // valid event venue
        assertTrue(EventVenue.isValidVenue("kent ridge")); // alphabets only
        assertTrue(EventVenue.isValidVenue("12345")); // numbers only
        assertTrue(EventVenue.isValidVenue("PlaCe")); // alphanumeric characters
        assertTrue(EventVenue.isValidVenue("NUS @ Kent Ridge")); // alphanumeric with symbols
        assertTrue(EventVenue.isValidVenue("PARLIAMENT")); // with capital letters
    }


    @Test
    void eventVenueEquals() {
        assertEquals(new EventVenue("niceVenue"), new EventVenue("niceVenue"));
        assertNotEquals(new EventVenue("niceVenue"), new EventVenue("nicevenue")); //Different casing
        assertNotEquals(new EventVenue("Nice Venue   "), new EventVenue("Nice Venue "));
    }
}
