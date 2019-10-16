package seedu.address.itinerary.model.Event;

public class Location {
    public static final String MESSAGE_CONSTRAINTS =
            "Location should not be empty.";

    public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String location;

    public Location(String location) {
        this.location = location;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String location) {
        return location.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return location;
    }
}
