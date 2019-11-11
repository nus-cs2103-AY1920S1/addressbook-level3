package io.xpire.model.item;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import io.xpire.commons.util.AppUtil;
import io.xpire.commons.util.DateUtil;

/**
 * Represents an XpireItem's expiry date in xpire.
 * Guarantees: immutable; is valid as declared in {@link #isValidFormatExpiryDate(String)} (String)}
 */
public class ExpiryDate {
    public static final String DATE_FORMAT = "d/M/uuuu";
    public static final String MESSAGE_CONSTRAINTS_LOWER = "Only expiry dates that have not yet passed are accepted";
    public static final String MESSAGE_CONSTRAINTS_OUTDATED = "This expiry date is too outdated.";
    public static final String MESSAGE_CONSTRAINTS_UPPER = "Only expiry dates strictly within 100 years are accepted";
    public static final String MESSAGE_CONSTRAINTS_FORMAT =
            "Invalid expiry date. Expiry dates should be in the format dd/MM/yyyy.\n"
            + "Single-digit days and/or months are also accepted. \n"
            + "Example: 22/08/2020 or 2/2/2020 or 02/2/2020";
    public static final String MESSAGE_CONSTRAINTS_NO_SUCH_DATE =
            "Invalid expiry date. This expiry date does not exist.";
    private static final String EXPIRED = "Expired!";
    private static final String DAYS_LEFT = "%d day%s left";
    private static final LocalDate CENTURY_LATER = DateUtil.getCurrentDate().plusYears(100);
    private final LocalDate date;

    /**
     * Constructs a {@code ExpiryDate}.
     *
     * @param expiryDate A valid expiryDate.
     */
    public ExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        AppUtil.checkArgument(isValidFormatExpiryDate(expiryDate), MESSAGE_CONSTRAINTS_FORMAT);
        this.date = DateUtil.convertStringToDate(expiryDate);
    }

    //@@author febee99
    /**
     * Returns true if a given string is a valid expiry date with format d/M/yyyy.
     */
    public static boolean isValidFormatExpiryDate(String date) {
        return date.matches("\\d{1,2}/\\d{1,2}/\\d{4}");
    }


    /**
     * Returns true if a given string is a valid expiry date that exists in the calendar.
     */
    public static boolean isValidExpiryDate(String date) {
        try {
            DateUtil.DATE_TIME_FORMATTER.parse(date);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if a given string is a valid expiry date that has not yet passed.
     */
    public static boolean isValidLowerRangeExpiryDate(String date) {
        LocalDate d = DateUtil.convertStringToDate(date);
        return d.isAfter(DateUtil.getCurrentDate());
    }

    //@@author xiaoyu-nus
    /**
     * Returns true if a given string is a valid expiry date within a hundred years.
     */
    public static boolean isValidUpperRangeExpiryDate(String date) {
        LocalDate d = DateUtil.convertStringToDate(date);
        return d.isBefore(CENTURY_LATER);
    }

    /**
     * Checks if the ExpiryDate has passed.
     * @return true if item is expired; false otherwise.
     */
    public boolean isPassed() {
        return Long.parseLong(getStatus()) <= 0;
    }

    /**
     * Computes the number of days between the ExpiryDate and the current date.
     */
    public String getStatus() {
        long offset = DateUtil.getOffsetDays(DateUtil.getCurrentDate(), this.date);
        return String.valueOf(offset);
    }

    //@@author JermyTan
    /**
     * Returns the number of days left to the expiry date given the specified date.
     * If specified date is not earlier than expiry date, an "Expired!" status will be returned.
     *
     * @param current Specified date.
     * @return Status of the expiry date.
     */
    public String getStatus(LocalDate current) {
        long offset = DateUtil.getOffsetDays(current, this.date);
        return offset > 0 ? String.format(DAYS_LEFT, offset, offset == 1 ? "" : "s") : EXPIRED;
    }

    /**
     * Returns the string representation of the expiry date with its status.
     *
     * @return String representation of expiry date with its status.
     */
    public String toStringWithCountdown() {
        return String.format("%s (%s)", this.toString(), this.getStatus(DateUtil.getCurrentDate()));
    }

    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return DateUtil.convertDateToString(this.date);
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
