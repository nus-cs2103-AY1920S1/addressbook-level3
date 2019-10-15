package seedu.deliverymans.model.location;

/**
 * Represents a location in the map.
 */
public class Location {

    private String name;
    private int x;
    private int y;

    Location(String locationName, int xCoordinate, int yCoordinate) {
        name = locationName;
        x = xCoordinate;
        y = yCoordinate;
    }

    public String getName() {
        return name;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
