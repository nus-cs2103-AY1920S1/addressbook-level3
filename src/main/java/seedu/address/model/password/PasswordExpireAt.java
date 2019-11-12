package seedu.address.model.password;

import static java.util.Objects.requireNonNull;

import java.util.Date;

import seedu.address.model.util.DateUtil;



/**
 * Represents a Password's expiry date and time in SecureIT.
 */
public class PasswordExpireAt {
    public final Date value;
    /**
     * Constructs an {@code PasswordExpireAt} field.
     *
     * @param expireAt the date and time the password will expire at.
     */
    public PasswordExpireAt(Date expireAt) {
        requireNonNull(expireAt);
        value = expireAt;
    }

    @Override
    public String toString() {
        return DateUtil.formatDateForDisplay(value);
    }

    public long getDays() {
        Date d1 = new Date();
        return DateUtil.findDaysPasswordExpireAt(d1, value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PasswordExpireAt // instanceof handles nulls
                && value.equals(((PasswordExpireAt) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
