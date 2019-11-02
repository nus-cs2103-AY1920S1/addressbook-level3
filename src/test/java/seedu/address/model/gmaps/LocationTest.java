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
    void setAndGetValidLocation() {

        location.setValidLocation("NUS_LTFOO");
        assertEquals(location.getValidLocation(), "NUS_LTFOO");
    }

    @Test
    void getLocationName() {
        assertEquals(location.getLocationName(), "LTFOO");
    }

    @Test
    void testEquals() {
        location.setValidLocation("NUS_LTFOO");
        Location location2 = new Location("LTFOO");
        location2.setValidLocation("NUS_LTFOO");
        assertEquals(location, location2);
    }

    @Test
    void placeId() {
        location.setPlaceId("ChIJMW2gnpUb2jERlUYTYSaawlc");
        assertEquals("ChIJMW2gnpUb2jERlUYTYSaawlc", location.getPlaceId());
    }

    @Test
    void testToString() {
        location.setValidLocation("NUS_LTFOO");
        assertEquals(location.toString(), "Location: LTFOO, Google recognised location: NUS_LTFOO Place ID: null");
    }

    @Test
    void testLatLng() {
        location.setLat("2.345");
        location.setLng("1.234");
        assertEquals("2.345,1.234", location.getLatLng());
    }
}
