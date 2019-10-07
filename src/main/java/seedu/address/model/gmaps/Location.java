package seedu.address.model.gmaps;

import static java.util.Objects.requireNonNull;

/**
 * Represent a location object for the various venues in NUS
 */

public class Location {

    public final String locationName;
    private String latitude;
    private String longitude;

    public Location(String locationName, String latitude, String longitude) {
        requireNonNull(locationName);
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Location(String locationName) {
        requireNonNull(locationName);
        this.locationName = locationName;
    }

    @Override

    public boolean equals(Object other) {
        return other == this || other instanceof Location
                && ((Location) other).locationName.equals(locationName)
                && ((Location) other).latitude.equals(latitude)
                && ((Location) other).longitude.equals(longitude);
    }

    @Override
    public String toString() {
        return "Location: " + locationName + " at latitude: " + latitude + ", longitude: " + longitude;
    }
}
