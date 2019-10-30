package seedu.ezwatchlist.model.show;

import static java.util.Objects.isNull;

/**
 * Represents an Genre of a show in the watchlist.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGenreName(String)}
 */
public class Genre {
    public static final String MESSAGE_CONSTRAINTS =
            "Genre names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the genre name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public static final String DEFAULT_VALUE = "na";

    public final String genreName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Genre(String name) {
        if (isNull(name)) {
            name = DEFAULT_VALUE;
        }
        genreName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidGenreName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getGenreName() {
        return genreName;
    }

    @Override
    public String toString() {
        return genreName;
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
}

