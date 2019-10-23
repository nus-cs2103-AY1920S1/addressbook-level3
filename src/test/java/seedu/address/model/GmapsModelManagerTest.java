package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.internal.gmaps.ClosestLocationSyntax;

class GmapsModelManagerTest {
    private GmapsModelManager gmapsModelManager;
    @BeforeEach
    void setUp() {
        gmapsModelManager = new GmapsModelManager();
    }

    @Test
    void closestLocationData() {
        ArrayList<String> locationNameList = new ArrayList<>(Arrays.asList("LT17", "LT17", "LT17"));
        Hashtable<String, Object> result = gmapsModelManager.closestLocationData(locationNameList);
        assertEquals((String) result.get(ClosestLocationSyntax.FIRST_CLOSEST), "LT17");
        assertEquals((long) result.get(ClosestLocationSyntax.FIRST_CLOSEST_AVG_TIME), (long) 0);
    }

    @Test
    void closestLocationDataString() {
        ArrayList<String> locationNameList = new ArrayList<>(Arrays.asList("LT17", "LT17", "LT17"));
        String result = gmapsModelManager.closestLocationDataString(locationNameList);
        String expectedResult = "First closest location: LT17 | Average travelling time 0\n"
                + "Second closest location: LT19 | Average travelling time 4\n"
                + "Third closest location: AS4 | Average travelling time 70\n";
        assertEquals(expectedResult, result);
    }
}
