package io.xpire.model.item;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import io.xpire.commons.util.DateUtil;

/**
 * Represents an Item's reminder date in the expiry date tracker.
 */
public class ReminderDate {

    public static final String DATE_FORMAT = "d/M/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private LocalDate date;

    /**
     * Constructs a {@code ReminderDate}.
     *
     * @param date A valid ReminderDate.
     */
    public ReminderDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Returns true if a given string is a valid expiry date with format d/M/yyyy.
     */
    public static boolean isValidReminderDate(String date) {
        return DateUtil.convertStringToDate(date, DATE_FORMAT) != null;
    }

    @Override
    public String toString() {
        return "Reminder on: " + this.date.format(DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderDate // instanceof handles nulls
                && this.date.equals(((ReminderDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return this.date.hashCode();
    }

}
