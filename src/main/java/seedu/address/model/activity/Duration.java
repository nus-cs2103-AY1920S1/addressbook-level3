package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.StringUtil;

/**
 * Represents the duration of an Activity in the application.
 * Guarantees: immutable;
 * @@author oscarsu97
 */
public class Duration {
    public static final String MESSAGE_CONSTRAINTS =
            "Value of duration should be a non-zero positive integer";
    public final Integer value;

    /**
     * Constructs a {@code Duration}.
     *
     * @param duration A valid duration value.
     */
    public Duration(Integer duration) {
        requireNonNull(duration);
        checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS);
        value = duration;
    }

    /**
     * Returns true if a given integer is a valid duration.
     */
    public static boolean isValidDuration(Integer test) {
        return StringUtil.isNonZeroUnsignedInteger(test.toString()) && (test >= 0);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Duration // instanceof handles nulls
                && value.equals(((Duration) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
