package seedu.ezwatchlist.model.show;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Show's watched or not field in the watchlist.
 * Guarantees: is valid as declared in {@link #isValidIsWatched(boolean)}
 */
public class IsWatched {

    public static final String MESSAGE_CONSTRAINTS =
            "Shows IsWatched should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the show must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final boolean value;

    /**
     * Constructs a {@code Name}.
     *
     * @param isWatched A valid boolean.
     */
    public IsWatched(boolean isWatched) {
        requireNonNull(isWatched);
        checkArgument(isValidIsWatched(isWatched), MESSAGE_CONSTRAINTS);
        value = isWatched;
    }

    /**
     * Returns true if a given boolean is a valid isWatched.
     */
    public static boolean isValidIsWatched(boolean test) {
        return true;
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
