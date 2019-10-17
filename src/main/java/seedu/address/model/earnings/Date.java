package seedu.address.model.earnings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a date of earnings.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateNum(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should only contain the date numbers that are valid (i.e. 03/05/2020), and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1"
            + "|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$"
            + "|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])"
            + "|(?:(?:16|[2468][048]|[3579][26])00))))$"
            + "|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    public final String dateNum;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date number.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDateNum(date), MESSAGE_CONSTRAINTS);
        dateNum = date;
    }

    /**
     * Returns true if a given string is a valid week number.
     */
    public static boolean isValidDateNum(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return dateNum;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Week // instanceof handles nulls
                && dateNum.equals(((Date) other).dateNum)); // state check
    }

    @Override
    public int hashCode() {
        return dateNum.hashCode();
    }

}
