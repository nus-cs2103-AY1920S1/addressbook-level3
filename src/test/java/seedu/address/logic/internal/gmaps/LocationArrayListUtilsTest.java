package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.gmaps.Location;

public class LocationArrayListUtilsTest {
    @Test
    public void getSubList_test() {
        ArrayList<Location> locationArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Location currLocation = new Location(Integer.toString(i));
            locationArrayList.add(currLocation);
        }
        ArrayList<Location> newlocationArrayList = LocationArrayListUtils.getSubList(locationArrayList, 3, 5);
        assertTrue(newlocationArrayList.size() == 3);
        for (int i = 0; i < newlocationArrayList.size(); i++) {
            assertTrue(Integer.parseInt(newlocationArrayList.get(i).locationName) == i + 3);
        }
    }
}
