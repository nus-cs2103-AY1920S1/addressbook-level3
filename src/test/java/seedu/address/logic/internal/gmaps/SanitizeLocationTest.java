package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.ConnectException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.TimeBookInvalidLocation;
import seedu.address.stubs.GmapsApiStub;

class SanitizeLocationTest {
    private SanitizeLocation sanitizeLocation;

    @BeforeEach
    void init() {
        sanitizeLocation = new SanitizeLocation(new GmapsApiStub());
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
