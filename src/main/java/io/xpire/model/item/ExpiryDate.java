package io.xpire.model.item;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import io.xpire.commons.util.AppUtil;
import io.xpire.commons.util.DateUtil;

/**
 * Represents an Item's expiry date in xpire.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpiryDate(String)} (String)}
 */
public class ExpiryDate {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String EXPIRED = "Expired!";
    private static final String DAYS_LEFT = "%d day%s left";
    public static final String MESSAGE_CONSTRAINTS =
            "Expiry dates should only contain numbers, in the format " + DATE_FORMAT;

    private final LocalDate date;

    /**
     * Constructs a {@code ExpiryDate}.
     *
     * @param expiryDate A valid expiryDate.
     */
    public ExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        AppUtil.checkArgument(isValidExpiryDate(expiryDate), MESSAGE_CONSTRAINTS);
        this.date = DateUtil.convertStringToDate(expiryDate, DATE_FORMAT);
    }

    /**
     * Returns true if a given string is a valid expiry date.
     */
    public static boolean isValidExpiryDate(String test) {
        return DateUtil.convertStringToDate(test, DATE_FORMAT) != null;
    }

    public String getStatus(LocalDate current) {
        int offset = DateUtil.getOffsetDays(current, this.date);
        return offset > 0 ? String.format(DAYS_LEFT, offset, offset == 1 ? "" : "s") : EXPIRED;
    }

    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return DateUtil.convertDateToString(this.date, DATE_FORMAT);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ExpiryDate)) {
            return false;
        } else {
            ExpiryDate other = (ExpiryDate) obj;
            return this.date.equals(other.date);
        }
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
