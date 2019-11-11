package seedu.address.model.earnings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a Tutor's earnings count status, aka how many
 * times to add in the earnings list automatically.
 * Guarantees: immutable; is valid as declared in {@link #isValidCount(String)}
 */
public class Count {

    public static final String MESSAGE_CONSTRAINTS =
            "Count numbers should only contain positive numbers and that is less than or equal to 13.";
    public static final String VALIDATION_REGEX = "^([0-9]|[1-9][0-3]|13)$";

    public final String count;

    /**
     * Constructs a {@code Count}.
     *
     * @param count A valid count.
     */
    public Count(String count) {
        requireNonNull(count);
        checkArgument(isValidCount(count), MESSAGE_CONSTRAINTS);
        this.count = count;
    }

    /**
     * Returns true if a given string is a valid count.
     */
    public static boolean isValidCount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return count;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Count // instanceof handles nulls
                && count.equals(((Count) other).count)); // state check
    }

    @Override
    public int hashCode() {
        return count.hashCode();
    }

    /**
     * Changes the normal date format of dd/MM/yyyy
     * to names of days (i.e. Monday, Friday, etc.).
     */
    public static String parseDateToDays(String otherDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(otherDate);
            String day = new SimpleDateFormat("EEEEE").format(date);
            return day;

        } catch (ParseException e) {
            return "";
        }
    }
}
