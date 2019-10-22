package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.net.ConnectException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.model.gmaps.LocationGraph;

class ProcessLocationGraphTest {
    private ProcessLocationGraph processLocationGraph;
    private LocationGraph locationGraph;
    @BeforeEach
    void init() throws ConnectException, TimeBookInvalidState {
        ProcessVenues processVenues = new ProcessVenues().process();
        locationGraph =
                new LocationGraph(processVenues.getLocations(), processVenues.getValidLocationList());
        processLocationGraph = new ProcessLocationGraph(locationGraph);
    }

    @Test
    void populateMatrix() throws TimeBookInvalidState, ConnectException {
        processLocationGraph.process();
        ArrayList<ArrayList<Long>> distanceMatrix = locationGraph.getDistanceMatrix();
        assertDoesNotThrow(() -> {
            try {
                for (int i = 0; i < distanceMatrix.size(); i++) {
                    if (distanceMatrix.get(i).get(i) != 0) {
                        System.out.println(distanceMatrix.get(i).get(i));
                        throw new IllegalValueException("Venue i to venue i take 0 time");
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

    }
}
