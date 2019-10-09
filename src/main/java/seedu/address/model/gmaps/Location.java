package seedu.address.model.gmaps;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;

/**
 * Represent a location object for the various venues in NUS
 */

public class Location implements Serializable {

    private final String locationName;
    private String googleRecognisedLocation = null;

    public Location(String locationName) {
        requireNonNull(locationName);
        this.locationName = locationName;
    }

    public void setGoogleRecognisedLocation(String googleRecognisedLocation) {
        this.googleRecognisedLocation = googleRecognisedLocation;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getGoogleRecognisedLocation() {
        return googleRecognisedLocation;
    }

    @Override

    public boolean equals(Object other) {
        return other == this || other instanceof Location
                && ((Location) other).getLocationName().equals(locationName)
                && ((Location) other).getGoogleRecognisedLocation().equals(googleRecognisedLocation);
    }

    @Override
    public String toString() {
        return "Location: " + locationName + ", Google recognised location: " + googleRecognisedLocation;
    }
}
