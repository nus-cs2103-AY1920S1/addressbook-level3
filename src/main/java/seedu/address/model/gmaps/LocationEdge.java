package seedu.address.model.gmaps;

import static java.util.Objects.requireNonNull;

/**
 * This class represents the edge of the location graph.
 * Where the edge stores the information of the travelling time and location it is
 * connected to.
 */

public class LocationEdge {

    public final Location location;

    public final int travellingTime;

    public LocationEdge(Location location, int travellingTime) {
        requireNonNull(location);
        this.location = location;
        this.travellingTime = travellingTime;
    }

    @Override
    public String toString() {
        return travellingTime + "min to reach " + location.locationName;
    }
}
