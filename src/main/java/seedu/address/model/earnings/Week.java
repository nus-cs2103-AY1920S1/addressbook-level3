package seedu.address.model.earnings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a week.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeekNum(String)}
 */
public class Week {

    public static final String MESSAGE_CONSTRAINTS =
            "Weeks should only contain the week numbers that are valid (i.e. 1-13), and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String weekNum;

    /**
     * Constructs a {@code Week}.
     *
     * @param week A valid week number.
     */
    public Week(String week) {
        requireNonNull(week);
        checkArgument(isValidWeekNum(week), MESSAGE_CONSTRAINTS);
        weekNum = week;
    }

    /**
     * Returns true if a given string is a valid week number.
     */
    public static boolean isValidWeekNum(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return weekNum;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Week // instanceof handles nulls
                && weekNum.equals(((Week) other).weekNum)); // state check
    }

    @Override
    public int hashCode() {
        return weekNum.hashCode();
    }

}
