package seedu.deliverymans.model.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contains all locations.
 */
public class LocationMap {
    private List<Location> locations = new ArrayList<>(List.of(
            new Location("Jurong", 1, 1),
            new Location("Marina", 2, 0),
            new Location("Changi", 3, 1)
    ));

    public Optional<Location> getLocation(String name) {
        String query = name.toLowerCase();
        return locations.stream()
                .filter(location -> location.getName().toLowerCase().equals(query))
                .findAny();
    }

    public double getDistanceBetween(Location a, Location b) {
        double xDistance = b.getX() - a.getX();
        double yDistance = b.getY() - a.getY();
        return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }
}
