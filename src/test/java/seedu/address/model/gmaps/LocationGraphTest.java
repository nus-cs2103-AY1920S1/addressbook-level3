package seedu.address.model.gmaps;

import static org.junit.jupiter.api.Assertions.*;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.stubs.ProcessVenuesStub;


class LocationGraphTest {
    LocationGraph locationGraph;
    @BeforeEach
    void init() throws ConnectException, TimeBookInvalidState {
        locationGraph = new LocationGraph(new ProcessVenuesStub());
    }
    @Test
    void getGmapsRecognisedLocationList() {
        ArrayList<String> expectedLocationList = new ArrayList<>(Arrays.asList("NUS_FOO","NUS_BAR", "NUS_FOOBAR"));
        assertEquals(locationGraph.getGmapsRecognisedLocationList(), expectedLocationList);
    }

    @Test
    void getLocations() {
        Location location1 = new Location("Foo");
        location1.setGoogleRecognisedLocation("NUS_FOO");
        Location location2 = new Location("Foo-1234");
        location2.setGoogleRecognisedLocation("NUS_FOO");
        Location location3 = new Location("Foo_1234");
        location3.setGoogleRecognisedLocation("NUS_FOO");
        ArrayList<Location> expectedLocations = new ArrayList<Location>(Arrays.asList(location1, location2, location3));
        assertEquals(locationGraph.getLocations(), expectedLocations);
    }

    @Test
    void getLocationIndex() {
        assertEquals(locationGraph.getLocationIndex("Foo"), 0);
    }

    @Test
    void getLocationRow() {
        assertThrows(IllegalValueException.class, () -> locationGraph.getLocationRow(3));
    }

    @Test
    void getDistanceMatrix() {
        ArrayList<ArrayList<Long>> expectedDistanceMatrix =
                new ArrayList<>(Arrays.asList(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        assertEquals(locationGraph.getDistanceMatrix(), expectedDistanceMatrix);
    }

    @Test
    void setMatrixRow() throws TimeBookInvalidState, ConnectException {
        ArrayList<Long> row1 = new ArrayList<>(Arrays.asList((long) 1, (long) 2, (long) 3));
        ArrayList<ArrayList<Long>> actualMatrix= locationGraph.setMatrixRow(0,row1).getDistanceMatrix();
        ArrayList<ArrayList<Long>> expectedGraph =
                new ArrayList<>(Arrays.asList(row1, new ArrayList<>(), new ArrayList<>()));
        assertEquals(expectedGraph, actualMatrix);
    }
}