package seedu.address.model.gmaps;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.logic.internal.gmaps.ProcessVenues;


class LocationGraphTest {
    private ProcessVenues processVenues;
    private LocationGraph locationGraph;

    @BeforeEach
    void init() throws ConnectException, TimeBookInvalidState {
        processVenues = new ProcessVenues().process();
        locationGraph = new LocationGraph(processVenues.getLocations(), processVenues.getValidLocationList());
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
    void getLocationIndex() {
        assertEquals(locationGraph.getLocationIndex("NUS_LT17"), -1);
        assertEquals(locationGraph.getLocationIndex("LT17"), 142);
    }

    @Test
    void getLocationRow() {
        assertThrows(IllegalValueException.class, () -> locationGraph.getLocationRow(1000));
        assertDoesNotThrow(() -> locationGraph.getLocationRow(3));
    }

    @Test
    void getDistanceMatrix() {
        ArrayList<ArrayList<Long>> expectedDistanceMatrix =
                new ArrayList<>();
        for (int i = 0; i < locationGraph.getValidLocationList().size(); i++) {
            expectedDistanceMatrix.add(new ArrayList<>());
        }
        assertEquals(locationGraph.getDistanceMatrix(), expectedDistanceMatrix);
    }

    @Test
    void setMatrixRow() {
        ArrayList<ArrayList<Long>> expectedDistanceMatrix =
                new ArrayList<>();
        for (int i = 0; i < locationGraph.getValidLocationList().size(); i++) {
            expectedDistanceMatrix.add(new ArrayList<>());
        }
        expectedDistanceMatrix.get(0).add((long) 1);
        expectedDistanceMatrix.get(0).add((long) 2);
        expectedDistanceMatrix.get(0).add((long) 3);
        ArrayList<Long> row1 = new ArrayList<>(Arrays.asList((long) 1, (long) 2, (long) 3));
        ArrayList<ArrayList<Long>> actualMatrix = locationGraph.setMatrixRow(0, row1).getDistanceMatrix();

        assertEquals(expectedDistanceMatrix, actualMatrix);
    }
}
