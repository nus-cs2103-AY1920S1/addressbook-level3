package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

class ProcessLocationGraphTest {
    private ProcessLocationGraph processLocationGraph;
    @BeforeEach
    void init() {
        ProcessVenues processVenues = new ProcessVenues().process();
        processLocationGraph = new ProcessLocationGraph(processVenues.getValidLocationList());
    }

    @Test
    void populateMatrix() {
        ArrayList<ArrayList<Long>> distanceMatrix = processLocationGraph.getDistanceMatrix();
        assertDoesNotThrow(() -> {
            for (int i = 0; i < distanceMatrix.size() - 6; i++) {
                if (distanceMatrix.get(i).get(i) != 0) {
                    System.out.println(distanceMatrix.get(i).get(i));
                    throw new IllegalValueException("Venue i to venue i is not 0 meter away");
                }
            }
        });

    }
}
