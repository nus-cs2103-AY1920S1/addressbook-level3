package com.typee.model.engagement;

/**
 * Location class for Engagement
 */
public class Location {

    private String location;
    public static final String MESSAGE_CONSTRAINTS = "Locations cannot be blank.";

    public Location(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static boolean isValid(String string) {
        return !string.isBlank();
    }

    @Override
    public String toString() {
        return location;
    }

}
