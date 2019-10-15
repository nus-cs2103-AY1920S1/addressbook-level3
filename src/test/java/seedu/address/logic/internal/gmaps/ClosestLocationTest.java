package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import seedu.address.model.module.Venue;

class ClosestLocationTest {
    private ClosestLocation closestLocation;
    @BeforeEach
    void init() throws InterruptedException {
        closestLocation = new ClosestLocation();
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    @Order(1)
    void execute() {
        Venue venue1 = new Venue("LT17");
        Venue venue2 = new Venue("LT17");
        Venue venue3 = new Venue("LT17");
        ArrayList<Venue> venues = new ArrayList<>(Arrays.asList(venue1, venue2, venue3));
        assertEquals(closestLocation.closestLocationVenues(venues), "NUS_LT17");
    }

    @Test
    @Order(2)
    void closestLocation() {
        ArrayList<String> venues = new ArrayList<>(Arrays.asList("LT17", "LT17", "LT17"));
        assertEquals(closestLocation.closestLocationString(venues), "NUS_LT17");
    }
}
