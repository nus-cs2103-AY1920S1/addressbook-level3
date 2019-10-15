package seedu.address.logic.internal.gmaps;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

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
        try {
            await().until(isLoaded());
            Venue venue1 = new Venue("LT17");
            Venue venue2 = new Venue("LT17");
            Venue venue3 = new Venue("LT17");
            ArrayList<Venue> venues = new ArrayList<>(Arrays.asList(venue1, venue2, venue3));
            assertEquals(closestLocation.closestLocationVenues(venues), "NUS_LT17");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void closestLocation() {
        await().until(isLoaded());
        ArrayList<String> venues = new ArrayList<>(Arrays.asList("LT17", "LT17", "LT17"));
        assertEquals(closestLocation.closestLocationString(venues), "NUS_LT17");
    }

    private Callable<Boolean> isLoaded() {
        return () -> closestLocation != null; // The condition that must be fulfilled
    }
}
