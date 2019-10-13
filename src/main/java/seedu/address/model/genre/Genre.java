package seedu.address.model.genre;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Genre in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGenreName(String)}
 */
public class Genre {

    public static final String MESSAGE_CONSTRAINTS = "Genre names should be alphanumeric & hyphenated if needed";
    public static final String VALIDATION_REGEX = "[\\-\\p{Alnum}]+";

    public final String tagName;

    /**
     * Constructs a {@code Genre}.
     *
     * @param tagName A valid genre name.
     */
    public Genre(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidGenreName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid genre name.
     */
    public static boolean isValidGenreName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Genre // instanceof handles nulls
                && tagName.equals(((Genre) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
