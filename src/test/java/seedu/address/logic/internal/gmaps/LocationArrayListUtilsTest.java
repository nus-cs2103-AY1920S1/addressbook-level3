package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.gmaps.Location;

class LocationArrayListUtilsTest {
    ArrayList<Location> locationArrayList;
    @BeforeEach
    void init() {
        Location location1 = new Location("Foo");
        Location location2 = new Location("Bar");
        Location location3 = new Location("FooBarLT");
        locationArrayList = new ArrayList<>(Arrays.asList(location1, location2, location3));
    }

    @Test
    void getIndex() {
        assertEquals(LocationArrayListUtils.getIndex(locationArrayList, "Foo"), 0 );
        assertEquals(LocationArrayListUtils.getIndex(locationArrayList, "Bar"), 1 );
        assertEquals(LocationArrayListUtils.getIndex(locationArrayList, "FooBarLT"), 2 );
    }

    @Test
    void getIndexExceptionFlow() {
        InvalidParameterException e =
                assertThrows(
                        InvalidParameterException.class,
                        () -> LocationArrayListUtils.getIndex(locationArrayList, "BarFooBarFoo"));
        assertEquals(e.getMessage(), "Cannot find location BarFooBarFoo in arrayList");

    }

}