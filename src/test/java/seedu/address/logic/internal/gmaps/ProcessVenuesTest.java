package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.gmaps.Location;
import seedu.address.websocket.Cache;

/**
 * This class is used to test the ProcessVenuesTest class
 */
class ProcessVenuesTest {
    private ProcessVenues processVenues;
    @BeforeEach
    void init() {
        processVenues = new ProcessVenues();
    }

    @Test
    void getLocations() throws TimeBookInvalidState {
        assertThrows(TimeBookInvalidState.class, () -> processVenues.getLocations());
        ProcessVenues newProcessVenues = processVenues.process();
        Location lt17 = new Location("LT17");
        lt17.setValidLocation("NUS_LT17");
        lt17.setPlaceId("ChIJBeHqfnAb2jERL1OoMUzA7yE");
        assertTrue(newProcessVenues.getLocations().contains(lt17));
    }

    @Test
    void process() throws TimeBookInvalidState {
        ProcessVenues newProcessVenues = processVenues.process();
        assertNotNull(newProcessVenues.getLocations());
        assertNotNull(newProcessVenues.getValidLocationList());
    }

    @Test
    void getValidLocationList() {
        assertEquals(processVenues.getValidLocationList(), new ArrayList<>());
        ProcessVenues newProcessVenues = processVenues.process();
        Location lt17 = new Location("NUS_LT17");
        lt17.setValidLocation("NUS_LT17");
        lt17.setPlaceId("ChIJBeHqfnAb2jERL1OoMUzA7yE");
        assertTrue(newProcessVenues.getValidLocationList().contains(lt17));
    }

    @Test
    void imageSanityCheck() {
        processVenues.process();
        ArrayList<Location> validLocationList = processVenues.getValidLocationList();
        for (int i = 0; i < validLocationList.size(); i++) {
            String currValidLocation = validLocationList.get(i).getValidLocation();
            String path = Cache.writeImagePath(currValidLocation);
            assertTrue(FileUtil.isFileExists(Path.of(path)));
        }
    }
}
