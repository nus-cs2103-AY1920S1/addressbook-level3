package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.module.Venue;

class VenueUtilTest {

    @Test
    void venueListToString() {
        Venue venue1 = new Venue("Foo");
        Venue venue2 = new Venue("Bar");
        ArrayList<Venue> venues = new ArrayList<>(Arrays.asList(venue1, venue2));
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList("Foo", "Bar"));
        assertEquals(VenueUtil.venueListToString(venues), stringArrayList);
    }
}
