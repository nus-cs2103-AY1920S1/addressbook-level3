package seedu.address.itinerary.Model.Event;

/**
 * Location of the event in the itinerary.
 */
public class Location {
    public static final String MESSAGE_CONSTRAINTS =
            "We all know that you love typing on the CLI but the location of an event "
            + "should not contain any special characters and should be less than 100 characters.";

    public static final String VALIDATION_REGEX = "(.{0,100})";
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && location.equals(((Location) other).location)); // state check
    }
}
