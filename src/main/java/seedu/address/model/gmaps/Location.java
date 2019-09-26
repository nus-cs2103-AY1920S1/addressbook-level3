package seedu.address.model.gmaps;

/**
 * Represent a location object for the various venues in NUS
 */

public class Location {

    public final String locationName;
    public final String latitude;
    public final String longitude;

    public Location(String locationName, String latitude, String longitude) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override

    public String toString() {
        return "Location: " + locationName + " at latitude: " + latitude + ", longitude: " + longitude;
    }
}
