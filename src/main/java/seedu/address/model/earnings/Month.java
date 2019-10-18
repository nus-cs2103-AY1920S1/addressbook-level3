package seedu.address.model.earnings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a month.
 * Guarantees: immutable; is valid as declared in {@link #isValidMonth(String)}
 */
public class Month {

    public static final String MESSAGE_CONSTRAINTS =
            "Months should only contain the months full name "
                    + "that are valid (i.e. may, october), and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX =
            "january|february|march|april|may|june|july|august"
                    + "|september|october|november|december|jan|feb|mar|apr"
                    + "|jun|jul|aug|sep|oct|nov|dec";

    public final String monthName;

    /**
     * Constructs a {@code Month}.
     *
     * @param month A valid month.
     */
    public Month(String month) {
        requireNonNull(month);
        checkArgument(isValidMonth(month), MESSAGE_CONSTRAINTS);
        monthName = month;
    }

    /**
     * Returns true if a given string is a valid week number.
     */
    public static boolean isValidMonth(String test) {
        return test.toLowerCase().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return monthName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Week // instanceof handles nulls
                && monthName.equals(((Month) other).monthName)); // state check
    }

    @Override
    public int hashCode() {
        return monthName.hashCode();
    }

}
