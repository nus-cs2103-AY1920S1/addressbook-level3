package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.TimeBookInvalidState;

class ProcessVenuesTest {
    ProcessVenues processVenues;
    @BeforeEach
    void init() {
        processVenues = new ProcessVenues();
    }
    //TODO find a way to test happy flow
    @Test
    void getLocations() throws TimeBookInvalidState {
        assertThrows(TimeBookInvalidState.class, () -> processVenues.getLocations());
    }

    @Test
    void getGmapsRecognisedLocationList() {
        assertEquals(processVenues.getGmapsRecognisedLocationList(),  new ArrayList<>());
    }
}