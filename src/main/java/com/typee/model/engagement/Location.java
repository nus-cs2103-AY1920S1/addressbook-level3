package com.typee.model.engagement;

import java.util.Objects;

/**
 * Represents the location of an engagement.
 */
public class Location {

    public static final String MESSAGE_CONSTRAINTS = "Locations cannot be blank.";

    private String location;

    /**
     * Constructs a {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        this.location = location;
    }

    /**
     * Retrieves the location of the engagement.
     *
     * @return location in the form of a {@code String}.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns true if the {@code Location} name is valid.
     *
     * @param string {@code String} to be checked.
     * @return true if the {@code String} is a valid {@code Location}.
     */
    public static boolean isValid(String string) {
        return !string.isBlank();
    }

    @Override
    public String toString() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        // short-circuit
        if (this == o) {
            return true;
        } else if (o instanceof Location) {
            // check if objects represent the same locations.
            return location.equalsIgnoreCase(((Location) o).location);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
