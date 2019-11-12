package seedu.ezwatchlist.model.show;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Show's description in the watchlist.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Description should not be blank";

    public static final String DEFAULT_DESCRIPTION = "n.a.";
    /*
     * The first character of the show's description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String fullDescription;

    public Description() {
        fullDescription = "";
    }

    /**
     * Constructs a {@code Name}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        if (isNull(description) || description.isEmpty()) {
            description = DEFAULT_DESCRIPTION;
        }
        requireNonNull(description);
        fullDescription = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && fullDescription.equals(((Description) other).fullDescription)); // state check
    }

    @Override
    public int hashCode() {
        return fullDescription.hashCode();
    }
}
