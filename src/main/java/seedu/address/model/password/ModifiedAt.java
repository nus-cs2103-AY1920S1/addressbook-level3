package seedu.address.model.password;

import java.util.Date;

import seedu.address.model.util.DateUtil;

/**
 * Represents a Password's last modified date and time in SecureIT.
 */
public class ModifiedAt {
    public static final String MESSAGE_CONSTRAINTS = "LastCreated at should be in the format of dd/MM/yyyy HHmm";
    public final Date value;

    /**
     * Constructs an {@code CreatedAt} field.
     *
     * @param createdAt the date and time the password was created at.
     */
    public ModifiedAt(Date createdAt) {
        value = createdAt;
    }

    @Override
    public String toString() {
        return DateUtil.formatDate(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModifiedAt // instanceof handles nulls
                && value.equals(((ModifiedAt) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
