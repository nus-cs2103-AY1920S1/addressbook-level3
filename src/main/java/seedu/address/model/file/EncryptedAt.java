package seedu.address.model.file;

import java.util.Date;

import seedu.address.model.util.DateUtil;

/**
 * Represents a File's encryption date and time in SecureIT.
 */
public class EncryptedAt {

    public final Date value;

    /**
     * Constructs an {@code EncryptedAt} field.
     *
     * @param encryptedAt A file's encryption date and time.
     */
    public EncryptedAt(Date encryptedAt) {
        value = encryptedAt;
    }

    @Override
    public String toString() {
        return DateUtil.formatDate(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EncryptedAt // instanceof handles nulls
                && value.equals(((EncryptedAt) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
