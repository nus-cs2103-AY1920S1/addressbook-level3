package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.gmaps.Location;

class LocationArrayListUtilsTest {
    private ArrayList<Location> locationArrayList;
    @BeforeEach
    void init() {
        Location location1 = new Location("Foo");
        Location location2 = new Location("Bar");
        Location location3 = new Location("FooBarLT");
        locationArrayList = new ArrayList<>(Arrays.asList(location1, location2, location3));
    }

    @Test
    void getIndex() {
        assertEquals(LocationArrayListUtils.getIndex(locationArrayList, "Foo"), 0);
        assertEquals(LocationArrayListUtils.getIndex(locationArrayList, "Bar"), 1);
        assertEquals(LocationArrayListUtils.getIndex(locationArrayList, "FooBarLT"), 2);
    }

    @Test
    void getIndexNullElement() {
        locationArrayList.add(null);
        locationArrayList.add(new Location("BarFoo"));
        assertEquals(LocationArrayListUtils.getIndex(locationArrayList, "BarFoo"), 4);
    }

    @Test
    void getIndexExceptionFlow() {
        assertEquals(LocationArrayListUtils.getIndex(locationArrayList, "BarFooBarFoo"), -1);
    }

    @Test
    void containLocationNameHappyFlow() {
        ArrayList<Location> list = new ArrayList<>(Arrays.asList(new Location("Foo"),
                new Location("Bar")));
        assertTrue(LocationArrayListUtils.containLocationName(list, "Foo"));
        assertTrue(LocationArrayListUtils.containLocationName(list, "Bar"));
    }

    @Test
    void containLocationNameSadFlow() {
        ArrayList<Location> list = new ArrayList<>(Arrays.asList(new Location("Foo"),
                new Location("Bar")));
        assertFalse(LocationArrayListUtils.containLocationName(list, "FooBar"));
    }
}
