package seedu.address.model.genre;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Genre in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGenreName(String)}
 */
public class Genre {

    public static final String MESSAGE_CONSTRAINTS = "Genre names should be alphanumeric, "
            + "UPPERCASE & hyphenated if needed";
    public static final String VALIDATION_REGEX = "[[A-Z]\\d][\\-[A-Z]\\d]*";

    public final String genreName;

    /**
     * Constructs a {@code Genre}.
     *
     * @param genreName A valid genre name - will be converted to uppercase
     */
    public Genre(String genreName) {
        requireNonNull(genreName);
        String uppercaseGenreName = genreName.toUpperCase();
        checkArgument(isValidGenreName(uppercaseGenreName), MESSAGE_CONSTRAINTS);
        this.genreName = uppercaseGenreName;
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
                && genreName.equals(((Genre) other).genreName)); // state check
    }

    @Override
    public int hashCode() {
        return genreName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + genreName + ']';
    }

}
