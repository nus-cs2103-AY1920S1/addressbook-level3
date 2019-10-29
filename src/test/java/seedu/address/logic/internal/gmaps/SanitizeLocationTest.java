package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.TimeBookInvalidLocation;

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
        ArrayList<String> expectedValidLocationList =
                new ArrayList<String>(Arrays.asList("NUS_LT17", "NUS_AS5"));
        assertEquals(expectedValidLocationList, sanitizeLocation.getValidLocationList());
    }

    @Test
    void sanitize() throws TimeBookInvalidLocation {
        assertEquals(sanitizeLocation.sanitize("LT17"), "NUS_LT17");
        assertEquals(sanitizeLocation.sanitize("AS5-1234556"), "NUS_AS5");
        assertThrows(TimeBookInvalidLocation.class, () -> sanitizeLocation.sanitize("jcdhsajkfebadbs"));
    }
}
