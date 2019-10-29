package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.TimeBookInvalidLocation;
import seedu.address.model.gmaps.Location;

class SanitizeLocationTest {
    private SanitizeLocation sanitizeLocation;

    @BeforeEach
    void init() {
        sanitizeLocation = new SanitizeLocation();
    }

    @Test
    void getValidLocationList() throws TimeBookInvalidLocation {
        sanitizeLocation.sanitize("LT17");
        sanitizeLocation.sanitize("AS5-1234556");
        sanitizeLocation.sanitize("AS5/1234556");
        sanitizeLocation.sanitize("AS5_1234556");
        assertThrows(TimeBookInvalidLocation.class, () -> sanitizeLocation.sanitize("BARFOO"));
        Location lt17 = new Location("NUS_LT17");
        lt17.setValidLocation("NUS_LT17");
        lt17.setPlaceId("ChIJBeHqfnAb2jERL1OoMUzA7yE");
        Location as5 = new Location("NUS_AS5");
        as5.setValidLocation("NUS_AS5");
        as5.setPlaceId("ChIJD8PBzVAa2jER4aeSkEGQwK0");

        ArrayList<Location> expectedValidLocationList =
                new ArrayList<Location>(Arrays.asList(lt17, as5));
        assertEquals(expectedValidLocationList, sanitizeLocation.getValidLocationList());
    }

    @Test
    void sanitize() throws TimeBookInvalidLocation {
        Location lt17 = new Location("LT17");
        lt17.setValidLocation("NUS_LT17");
        lt17.setPlaceId("ChIJBeHqfnAb2jERL1OoMUzA7yE");
        Location as5 = new Location("AS5-1234556");
        as5.setValidLocation("NUS_AS5");
        as5.setPlaceId("ChIJD8PBzVAa2jER4aeSkEGQwK0");
        assertEquals(sanitizeLocation.sanitize("LT17"), lt17);
        assertEquals(sanitizeLocation.sanitize("AS5-1234556"), as5);
        assertThrows(TimeBookInvalidLocation.class, () -> sanitizeLocation.sanitize("jcdhsajkfebadbs"));
    }
}
