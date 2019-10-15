package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.TimeBookInvalidLocation;
import seedu.address.model.module.Venue;

class ClosestLocationTest {
    ClosestLocation closestLocation;
    @BeforeEach
    void init() {
        closestLocation = new ClosestLocation();
    }

    @Test
    void execute() {
        Venue venue1 = new Venue("LT17");
        Venue venue2 = new Venue("LT17");
        Venue venue3 = new Venue("LT17");
        ArrayList<Venue> venues = new ArrayList<>(Arrays.asList(venue1, venue2, venue3));
        assertEquals(closestLocation.closestLocationVenues(venues), "NUS_LT17");
    }

    @Test
    void closestLocation() throws IllegalValueException, TimeBookInvalidLocation {
        ArrayList<String> venues = new ArrayList<>(Arrays.asList("LT17", "LT17", "LT17"));
        assertEquals(closestLocation.closestLocationString(venues), "NUS_LT17");
    }
}