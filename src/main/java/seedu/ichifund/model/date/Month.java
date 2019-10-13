package seedu.ichifund.model.date;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.AppUtil.checkArgument;

public class Month {
    public static final String MESSAGE_CONSTRAINTS =
            "Month should only contain numbers, from 1 to 12";
    public static final String VALIDATION_REGEX = "[1-9]|1[0-2]";
    public final int monthNumber;

    /**
     * Constructs a {@code Month}.
     *
     * @param month A valid month number.
     */
    public Month(String month) {
        requireNonNull(month);
        checkArgument(isValidMonth(month), MESSAGE_CONSTRAINTS);
        monthNumber = Integer.parseInt(month);
    }

    /**
     * Returns true if a given string is a valid month.
     */
    public static boolean isValidMonth(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "" + monthNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Month // instanceof handles nulls
                && monthNumber == (((Month) other).monthNumber)); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
