package seedu.address.model.gmaps;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.logic.internal.gmaps.ProcessLocationGraph;
import seedu.address.logic.internal.gmaps.ProcessVenues;


class LocationGraphTest {
    private LocationGraph locationGraph;
    private ProcessVenues processVenues;
    private ProcessLocationGraph processLocationGraph;
    @BeforeEach
    void init() throws TimeBookInvalidState {
        processVenues = new ProcessVenues().process();
        processLocationGraph = new ProcessLocationGraph(processVenues.getValidLocationList());
        locationGraph = new LocationGraph(processVenues.getLocations(), processVenues.getValidLocationList(),
                processLocationGraph.getDistanceMatrix());
    }

    @Test
    void getValidLocationList() {
        assertEquals(locationGraph.getValidLocationList(), processVenues.getValidLocationList());
    }

    @Test
    void getLocations() {
        assertEquals(locationGraph.getLocations(), locationGraph.getLocations());
    }

    @Test
    void getLocationRow() {
        assertThrows(IllegalValueException.class, () -> locationGraph.getLocationRow(1000));
        assertDoesNotThrow(() -> locationGraph.getLocationRow(3));
    }

    @Test
    void getDistanceMatrix() {
        assertEquals(locationGraph.getDistanceMatrix(), processLocationGraph.getDistanceMatrix());
    }
}
