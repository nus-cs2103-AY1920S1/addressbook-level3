package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.model.display.detailwindow.ClosestCommonLocationData;
import seedu.address.model.gmaps.Location;
import seedu.address.model.gmaps.LocationGraph;

class ClosestLocationTest {
    private ClosestLocation closestLocation;
    @BeforeEach
    void init() throws TimeBookInvalidState {
        ProcessVenues processVenues;
        processVenues = new ProcessVenues().process();
        ArrayList<Location> locations = processVenues.getLocations();
        ArrayList<Location> validLocationList = processVenues.getValidLocationList();
        ProcessLocationGraph processLocationGraph = new ProcessLocationGraph(validLocationList);
        LocationGraph locationGraph = new LocationGraph(locations, validLocationList,
                processLocationGraph.getDistanceMatrix());
        closestLocation = new ClosestLocation(locationGraph);
    }

    @Test
    void closestLocationData() {
        ArrayList<String> locationNameList = new ArrayList<>(Arrays.asList("LT17", "LT17", "LT17"));
        ClosestCommonLocationData result = closestLocation.closestLocationData(locationNameList);
        assertEquals(result.getFirstClosest(), "LT17");
        assertEquals(result.getFirstAvg(), (long) 0);

    }

    @Test
    void closestLocationDataEdgeCase() {
        ArrayList<String> locationNameList = new ArrayList<>(Arrays.asList("canteen", "canteen", "canteen"));
        ClosestCommonLocationData result;
        assertDoesNotThrow(() -> closestLocation.closestLocationData(locationNameList));
        result = closestLocation.closestLocationData(locationNameList);
        assertFalse(result.isOk());

        ArrayList<String> locationNameList1 = new ArrayList<>(Arrays.asList("canteen", "LT17", "canteen"));
        ClosestCommonLocationData result1;
        assertDoesNotThrow(() -> closestLocation.closestLocationData(locationNameList1));
        result1 = closestLocation.closestLocationData(locationNameList1);
        assertTrue(result1.isOk());
        System.out.println(closestLocation.closestLocationDataString(locationNameList1));
        assertEquals(result1.getFirstClosest(), "LT17");

        ArrayList<String> locationNameList2 = new ArrayList<>(Arrays.asList("canteen", "S42", "canteen"));
        ClosestCommonLocationData result2;
        assertDoesNotThrow(() -> closestLocation.closestLocationData(locationNameList2));
        result2 = closestLocation.closestLocationData(locationNameList2);
        assertFalse(result2.isOk());

        ArrayList<String> locationNameList3 = new ArrayList<>();
        ClosestCommonLocationData result3;
        assertDoesNotThrow(() -> closestLocation.closestLocationData(locationNameList3));
        result3 = closestLocation.closestLocationData(locationNameList);
        assertFalse(result3.isOk());
    }

    @Test
    void closestLocation() {
        ArrayList<String> locationNameList = new ArrayList<>(Arrays.asList("LT17", "LT17", "LT17"));
        String result = closestLocation.closestLocationDataString(locationNameList);
        String expectedResult = "\nFirst closest location: LT17 | Average travelling distance/meters 0\n"
                + "Second closest location: LT19 | Average travelling distance/meters 11\n"
                + "Third closest location: LT8 | Average travelling distance/meters 224\n";
        assertEquals(expectedResult, result);
    }
}
