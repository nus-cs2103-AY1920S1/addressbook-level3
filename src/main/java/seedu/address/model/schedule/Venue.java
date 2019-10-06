package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Schedule's venue in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidVenue(String)}
 */
public class Venue implements Cloneable {

    public static final String MESSAGE_CONSTRAINTS = "Venue can take any string, and it should not be blank";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String venue;

    /**
     * Constructs an {@code venue}.
     *
     * @param venue A valid venue.
     */
    public Venue(String venue) {
        requireNonNull(venue);
        checkArgument(isValidVenue(venue), MESSAGE_CONSTRAINTS);
        this.venue = venue;
    }

    /**
     * Returns true if a given string is a valid venue.
     */
    public static boolean isValidVenue(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return venue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Venue // instanceof handles nulls
                && venue.equals(((Venue) other).venue)); // state check
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Venue(new String(venue));
    }

    @Override
    public int hashCode() {
        return venue.hashCode();
    }

}
