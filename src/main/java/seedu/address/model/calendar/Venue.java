package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Venue in the calender entry.
 */
public class Venue {
    public static final String MESSAGE_CONSTRAINTS = "Venue can take any values, and it should not be blank";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Venue}.
     *
     * @param venue A valid venue.
     */
    public Venue(String venue) {
        requireNonNull(venue);
        checkArgument(isValidVenue(venue), MESSAGE_CONSTRAINTS);
        value = venue;
    }

    /**
     * Returns true if a given string is a valid venue.
     */
    public static boolean isValidVenue(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Venue
                && value.equals(((Venue) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
