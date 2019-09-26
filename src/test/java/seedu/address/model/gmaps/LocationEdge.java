package seedu.address.model.gmaps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test LocationEdge object.
 */
public class LocationEdge {

    @Test
    public void test_toString() {
        Location location = new Location("Singapore", "1.2345", "6.7890");
        LocationEdge locationEdge = new LocationEdge(location, 123);
        assertTrue(locationEdge.toString().equals("30 min to reach Singapore"));
    }
}
