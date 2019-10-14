package com.typee.model.engagement;

import java.time.LocalDateTime;

/**
 * Location class for Engagement
 */
public class Location {

    public static String location;

    public Location(String location) {
        this.location = location;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        Location.location = location;
    }

}
