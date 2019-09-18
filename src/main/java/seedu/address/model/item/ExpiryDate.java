package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents an Item's expiry date in the expiry date tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpiryDate(String)} (String)}
 */
public class ExpiryDate {


    public static final String MESSAGE_CONSTRAINTS =
            "Expiry dates should only contain numbers, and they should be in the format dd/MM/yyyy";
    public static final String VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))"
        + "\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^"
        + "(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:"
        + "(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])"
        + "|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    private Date date;

    /**
     * Constructs a {@code ExpiryDate}.
     *
     * @param expiryDate A valid expiryDate.
     */
    public ExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        try {
            this.date = new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate);
            checkArgument(isValidExpiryDate(expiryDate), MESSAGE_CONSTRAINTS);
        } catch (ParseException e) {
            checkArgument(false, MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidExpiryDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpiryDate // instanceof handles nulls
                && date.equals(((ExpiryDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
