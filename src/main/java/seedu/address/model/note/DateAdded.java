package seedu.address.model.note;

import java.util.Date;

import seedu.address.model.util.DateUtil;


/**
 * Represents a File's encryption date and time in SecureIT.
 */
public class DateAdded {

    public static final String MESSAGE_CONSTRAINTS = "Date should be in the format of dd/MM/yyyy HHmm";

    public final Date value;

    /**
     * Constructs an {@code EncryptedAt} field.
     *
     * @param dateAdded A note's last modified date and time.
     */
    public DateAdded(Date dateAdded) {
        value = dateAdded;
    }

    @Override
    public String toString() {
        return DateUtil.formatDate(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateAdded // instanceof handles nulls
                && value.equals(((DateAdded) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
