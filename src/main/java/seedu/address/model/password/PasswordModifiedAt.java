package seedu.address.model.password;

import java.util.Date;

import seedu.address.model.util.DateUtil;

/**
 * Represents a Password's last modified date and time in SecureIT.
 */
public class PasswordModifiedAt {
    public static final String MESSAGE_CONSTRAINTS = "LastCreated at should be in the format of dd/MM/yyyy HHmm";
    public final Date value;

    /**
     * Constructs an {@code PasswordModifiedAt} field.
     *
     * @param modifiedAt the date and time the password was modified at.
     */
    public PasswordModifiedAt(Date modifiedAt) {
        value = modifiedAt;
    }

    @Override
    public String toString() {
        return DateUtil.formatDateForDisplay(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PasswordModifiedAt // instanceof handles nulls
                && value.equals(((PasswordModifiedAt) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
