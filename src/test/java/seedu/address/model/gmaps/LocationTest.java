package seedu.address.model.gmaps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationTest {
    private Location location;

    @BeforeEach
    void init() {
        location = new Location("LTFOO");
    }

    @Test
    void setGoogleRecognisedLocation() {

        location.setGoogleRecognisedLocation("NUS_LTFOO");
        assertEquals(location.getGoogleRecognisedLocation(), "NUS_LTFOO");
    }

    @Test
    void getLocationName() {
        assertEquals(location.getLocationName(), "LTFOO");
    }

    @Test
    void testEquals() {
        location.setGoogleRecognisedLocation("NUS_LTFOO");
        Location location2 = new Location("LTFOO");
        location2.setGoogleRecognisedLocation("NUS_LTFOO");
        assertEquals(location, location2);
    }

    @Test
    void testToString() {
        location.setGoogleRecognisedLocation("NUS_LTFOO");
        assertEquals(location.toString(), "Location: LTFOO, Google recognised location: NUS_LTFOO");
    }
}
