package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.*;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.TimeBookInvalidLocation;
import seedu.address.stubs.GmapsApiStub;

class SanitizeLocationTest {
    SanitizeLocation sanitizeLocation;

    @BeforeEach
    void init() {
        sanitizeLocation = new SanitizeLocation(new GmapsApiStub());
    }

    @Test
    void getValidLocationList() throws ConnectException, TimeBookInvalidLocation {
        sanitizeLocation.sanitize("FOO");
        sanitizeLocation.sanitize("FOO-12345");
        sanitizeLocation.sanitize("FOO_12345");
        sanitizeLocation.sanitize("BAR");
        assertThrows(TimeBookInvalidLocation.class, ()-> sanitizeLocation.sanitize("FOOLED"));
        ArrayList<String> expectedValidLocationList =
                new ArrayList<String>(Arrays.asList("NUS_FOO", "NUS_BAR"));
        assertEquals(expectedValidLocationList, sanitizeLocation.getValidLocationList());
    }

    @Test
    void sanitize() throws TimeBookInvalidLocation, ConnectException {
        assertEquals(sanitizeLocation.sanitize("FOO"), "NUS_FOO");
        assertEquals(sanitizeLocation.sanitize("FOO-12345"), "NUS_FOO");
        assertEquals(sanitizeLocation.sanitize("FOO_12345"), "NUS_FOO");
        assertEquals(sanitizeLocation.sanitize("BAR"), "NUS_BAR");
        assertThrows(TimeBookInvalidLocation.class, ()-> sanitizeLocation.sanitize("FOOLED"));
    }
}