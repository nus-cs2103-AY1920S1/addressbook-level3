package com.typee.model.engagement;

/**
 * Location class for Engagement
 */
public class Location {

    public static String location;
    public static final String MESSAGE_CONSTRAINTS = "Locations cannot be blank.";

    public Location(String location) {
        this.location = location;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        Location.location = location;
    }

    public static boolean isValid(String string) {
        return !string.isBlank();
    }

    @Override
    public String toString() {
        return location;
    }

}
