package seedu.address.model.file;

import java.util.Date;

import seedu.address.model.util.DateUtil;

/**
 * Represents a File's last modified date and time in SecureIT.
 */
public class ModifiedAt {

    public static final String MESSAGE_CONSTRAINTS = "Modified at should be in the format of dd/MM/yyyy HHmm";

    public final Date value;

    /**
     * Constructs an {@code ModifiedAt} field.
     *
     * @param encryptedAt A file's encryption date and time.
     */
    public ModifiedAt(Date encryptedAt) {
        value = encryptedAt;
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
