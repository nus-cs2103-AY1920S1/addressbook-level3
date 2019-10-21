package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.module.Venue;

class ClosestLocationTest {
    private ClosestLocation closestLocation;
    @BeforeEach
    void init() {
        closestLocation = new ClosestLocation();
    }

    @Test
    void execute() {

    }

    @Test
    void closestLocation() {
        try {
            ArrayList<String> venues = new ArrayList<>(Arrays.asList("LT17", "LT17", "LT17"));
            assertEquals(closestLocation.closestLocationData(venues), "NUS_LT17");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
