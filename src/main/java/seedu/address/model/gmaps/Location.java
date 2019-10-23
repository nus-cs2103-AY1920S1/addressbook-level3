package seedu.address.model.gmaps;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;

/**
 * Represent a location object for the various venues in NUS
 */

public class Location implements Serializable {
    private static final long serialVersionUID = 6529685098267757691L;
    private final String locationName;
    private String validLocation = null;

    public Location(String locationName) {
        requireNonNull(locationName);
        this.locationName = locationName;
    }

    public void setValidLocation(String googleRecognisedLocation) {
        this.validLocation = googleRecognisedLocation;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getValidLocation() {
        return validLocation;
    }

    @Override

    public boolean equals(Object other) {
        return other == this || other instanceof Location
                && ((Location) other).getLocationName().equals(locationName)
                && ((Location) other).getValidLocation().equals(validLocation);
    }

    @Override
    public String toString() {
        return "Location: " + locationName + ", Google recognised location: " + validLocation;
    }
}
