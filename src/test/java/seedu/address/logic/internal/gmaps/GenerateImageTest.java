package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.model.gmaps.Location;
import seedu.address.websocket.util.UrlUtil;

class GenerateImageTest {

    @Test
    void executeNoKeyThrowError() {
        assertThrows(TimeBookInvalidState.class, () -> new GenerateImage(new ArrayList<>()).execute());
    }

    @Test
    void executeWKey_emptyLocations() {
        UrlUtil.setGmapsApiKey("TEST KEY");
        assertThrows(TimeBookInvalidState.class, () -> new GenerateImage(new ArrayList<>()).execute());
        UrlUtil.setGmapsApiKey("");
    }

    @Test
    void executeHappyFlow() {
        UrlUtil.setGmapsApiKey("TEST KEY");
        Location validLocationTest = new Location("foo");
        validLocationTest.setLat("latTest");
        validLocationTest.setLng("lngTest");
        assertDoesNotThrow(() -> new GenerateImage(new ArrayList<>(Arrays.asList(validLocationTest))).execute());
        UrlUtil.setGmapsApiKey("");
    }


}
