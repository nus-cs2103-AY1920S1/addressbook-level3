package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.TimeBookInvalidLocation;
import seedu.address.stubs.GmapsApiStub;

class SanitizeLocationTest {
    private SanitizeLocation sanitizeLocation;

    @BeforeEach
    @Order(1)
    void init() {
        sanitizeLocation = new SanitizeLocation(new GmapsApiStub());
    }

    @Test
    @Order(2)
    void getValidLocationList() throws ConnectException, TimeBookInvalidLocation {
        sanitizeLocation.sanitize("FOO");
        sanitizeLocation.sanitize("FOO-12345");
        sanitizeLocation.sanitize("BAR");
        ArrayList<String> expectedValidLocationList =
                new ArrayList<String>(Arrays.asList("NUS_FOO", "NUS_BAR"));
        assertEquals(expectedValidLocationList, sanitizeLocation.getValidLocationList());
    }

    @Test
    @Order(3)
    void sanitize() throws TimeBookInvalidLocation, ConnectException {
        assertEquals(sanitizeLocation.sanitize("FOO"), "NUS_FOO");
        assertEquals(sanitizeLocation.sanitize("FOO-12345"), "NUS_FOO");
        assertEquals(sanitizeLocation.sanitize("BAR"), "NUS_BAR");
    }
}
