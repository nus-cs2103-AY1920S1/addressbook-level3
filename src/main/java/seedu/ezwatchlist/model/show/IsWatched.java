package seedu.ezwatchlist.model.show;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Show's watched or not field in the watchlist.
 * Guarantees: is valid as declared in {@link #isValidIsWatched(String)}
 */
public class IsWatched {

    public static final String MESSAGE_CONSTRAINTS =
            "The watched value of a show can only be 'true' or 'false' (case in-sensitive)";

    /*
     * The first character of the show must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final boolean value;

    public IsWatched() {
        value = false;
    }

    /**
     * Constructs a {@code Name}.
     *
     * @param isWatched A valid boolean.
     */
    public IsWatched(String isWatched) {
        requireNonNull(isWatched);
        checkArgument(isValidIsWatched(isWatched), MESSAGE_CONSTRAINTS);
        value = Boolean.parseBoolean(isWatched);
    }

    /**
     * Returns true if a given String is a valid isWatched.
     */
    public static boolean isValidIsWatched(String test) {
        return test.equals("true") || test.equals("false");
    }

    public boolean getIsWatchedBoolean() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsWatched // instanceof handles nulls
                && value == ((IsWatched) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value ? 1 : 0;
    }

}
