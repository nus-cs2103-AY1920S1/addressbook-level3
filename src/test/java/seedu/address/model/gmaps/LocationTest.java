package seedu.address.model.gmaps;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LocationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void test_toString() {
        Location location = new Location("Singapore");
        location.setGoogleRecognisedLocation("Singapore");
        assertTrue(location.toString().equals("Location: Singapore, Google recognised location: Singapore"));
    }
}
