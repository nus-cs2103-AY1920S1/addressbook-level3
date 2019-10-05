package io.xpire.model.item;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Item's reminder date in the expiry date tracker.
 */
public class ReminderDate {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

    @Override
    public String toString() {
        return "Reminder on: " + this.date.format(DATE_FORMAT);
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
