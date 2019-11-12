package seedu.address.model.note;

import java.util.Date;

import seedu.address.model.util.DateUtil;


/**
 * Represents a File's encryption date and time in SecureIT.
 */
public class DateModified {

    public static final String MESSAGE_CONSTRAINTS = "Date should be in the format of dd/MM/yyyy HHmm";

    public final Date value;

    /**
     * Constructs an {@code EncryptedAt} field.
     *
     * @param dateModified A note's last modified date and time.
     */
    public DateModified(Date dateModified) {
        value = dateModified;
    }

    public DateModified update() {
        return new DateModified(new Date());
    }

    @Override
    public String toString() {
        return DateUtil.formatDate(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateModified // instanceof handles nulls
                && value.equals(((DateModified) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
