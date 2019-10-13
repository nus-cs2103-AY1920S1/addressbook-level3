package seedu.ichifund.model.date;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.AppUtil.checkArgument;

/**
 * Represents a Year in a Date in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Year {
    public static final String MESSAGE_CONSTRAINTS =
            "Year should only contain years from 2000 to 9999";
    public static final String VALIDATION_REGEX = "[2-9]|\\d\\d\\d";
    public final int yearNumber;

    /**
     * Constructs a {@code Month}.
     *
     * @param year A valid year number.
     */
    public Year(String year) {
        requireNonNull(year);
        checkArgument(isValidYear(year), MESSAGE_CONSTRAINTS);
        yearNumber = Integer.parseInt(year);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidYear(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "" + yearNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Year // instanceof handles nulls
                && yearNumber == (((Year) other).yearNumber)); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
