package seedu.deliverymans.model.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contains all locations.
 */
public class LocationMap {

    public static final String MESSAGE_CONSTRAINTS =
            "Location should only be one of the following locations: "
            + "Jurong, Tuas, Woodlands, Bishan, City, Marina, Changi, Punggol";

    private static List<Location> locations = new ArrayList<>(List.of(
            new Location("Jurong", 1, 1),
            new Location("Tuas", 1, 1),
            new Location("Woodlands", 1, 1),
            new Location("Bishan", 1, 1),
            new Location("City", 1, 1),
            new Location("Marina", 2, 0),
            new Location("Changi", 3, 1),
            new Location("Punggol", 3, 1)
    ));

    public static Optional<Location> getLocation(String name) {
        String query = name.toLowerCase();
        return locations.stream()
                .filter(location -> location.getName().toLowerCase().equals(query))
                .findAny();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidLocation(String test) {
        return getLocation(test).isPresent();
    }

    public double getDistanceBetween(Location a, Location b) {
        double xDistance = b.getX() - a.getX();
        double yDistance = b.getY() - a.getY();
        return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }
}
