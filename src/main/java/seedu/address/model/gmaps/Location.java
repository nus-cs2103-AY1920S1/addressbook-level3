package seedu.address.model.gmaps;

import static java.util.Objects.requireNonNull;

/**
 * Represent a location object for the various venues in NUS
 */

public class Location {
    private String placeId = null;
    private String locationName;
    private String validLocation = null;

    public Location(String locationName) {
        requireNonNull(locationName);
        this.locationName = locationName;
    }


    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
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
        boolean isEqual = other == this || other instanceof Location
                && ((Location) other).getLocationName().equals(locationName)
                && ((Location) other).getValidLocation().equals(validLocation);
        if (placeId != null && ((Location) other).getPlaceId() != null) {
            isEqual = isEqual && ((Location) other).getPlaceId().equals(placeId);
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return "Location: " + locationName + ", Google recognised location: " + validLocation
                + " Place ID: " + placeId;
    }
}
