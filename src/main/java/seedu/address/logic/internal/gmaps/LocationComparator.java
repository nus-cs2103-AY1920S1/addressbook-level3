package seedu.address.logic.internal.gmaps;

import java.util.Comparator;

import seedu.address.model.gmaps.Location;

public class LocationComparator implements Comparator<Location> {
    public int compare(Location location1, Location location2) {
        return location1.getIndex() - location2.getIndex();
    }
    public boolean equals(Object obj) {
        return this == obj;
    }
}
