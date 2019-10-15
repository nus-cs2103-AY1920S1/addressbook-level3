package seedu.address.logic.internal.gmaps;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.module.Venue;

class ClosestLocationTest {
    private ClosestLocation closestLocation;
    @BeforeEach
    void init() throws InterruptedException {
        closestLocation = new ClosestLocation();
    }

    @Test
    void execute() {
        await().until(() ->
        {
            return closestLocation != null;
        });
        Venue venue1 = new Venue("LT17");
        Venue venue2 = new Venue("LT17");
        Venue venue3 = new Venue("LT17");
        ArrayList<Venue> venues = new ArrayList<>(Arrays.asList(venue1, venue2, venue3));
        assertEquals(closestLocation.closestLocationVenues(venues), "NUS_LT17");
    }

    @Test
    void closestLocation() {
        await().until(() ->
        {
            return closestLocation != null;
        });
        ArrayList<String> venues = new ArrayList<>(Arrays.asList("LT17", "LT17", "LT17"));
        assertEquals(closestLocation.closestLocationString(venues), "NUS_LT17");
    }
}
