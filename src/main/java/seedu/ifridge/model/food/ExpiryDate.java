package seedu.ifridge.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a Food's expiry date in the lists.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpiryDate(String)}
 */
public class ExpiryDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Expiry date should be valid and follow the format dd/MM/yyyy, and it should not be blank";

    /*
     * Regex that works on all valid date with format dd/MM/yyyy
     */
    public static final String VALIDATION_REGEX = "^(?:(?:31(\\/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/)"
            + "(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/)"
            + "0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])"
            + "|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])"
            + "(\\/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    public final String expiryDate;

    /**
     * Constructs a {@code ExpiryDate}.
     *
     * @param expiryDate A valid expiry date.
     */
    public ExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        checkArgument(isValidExpiryDate(expiryDate), ExpiryDate.MESSAGE_CONSTRAINTS);
        this.expiryDate = expiryDate;
    }

    /**
     * Returns true if a given string is a valid expiry date.
     */
    public static boolean isValidExpiryDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the expiry date in a date format.
     * @return date
     */
    public Date getValue() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(this.expiryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public String toString() {
        return expiryDate;
    }

    /**
     * Convert the string format of date to a Date object.
     * @return Returns a Date formatted object.
     * @throws ParseException if format is invalid.
     */
    public Date toDateFormat() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.parse(expiryDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpiryDate // instanceof handles nulls
                && expiryDate.equals(((ExpiryDate) other).expiryDate)); // state check
    }

    @Override
    public int hashCode() {
        return expiryDate.hashCode();
    }
}
