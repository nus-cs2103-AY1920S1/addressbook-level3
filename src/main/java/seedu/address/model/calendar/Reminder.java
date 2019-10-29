package seedu.address.model.calendar;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.model.DateTime;

/**
 * Represents a Reminder in the calendar.
 */
public class Reminder extends CalendarEntry {

    private Repetition repetition;

    public Reminder(Description description, DateTime dateTime, Repetition repetition) {
        super(description, dateTime);
        this.repetition = repetition;
    }

    public Repetition getRepetition() {
        return repetition;
    }

    /**
     * Returns true if both reminders have the same description, date and time; This defines a weaker notion of equality
     * between two reminders.
     */
    public boolean isSameReminder(Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }

        return otherReminder != null
            && otherReminder.getDescription().equals(getDescription())
            && otherReminder.getDateTime().equals(getDateTime());
    }

    /**
     * Returns true if the given time is between the starting time and the ending time.
     *
     * @throws IllegalArgumentException if the date of start and end are not the same.
     */
    @Override
    public boolean isBetween(DateTime start, DateTime end) throws IllegalArgumentException {
        if (!start.getDate().equals(end.getDate())) {
            throw new IllegalArgumentException("The starting date and the ending date should be the same.");
        }
        switch (repetition) {
        case Once:
            return getDateTime().isBetweenDateTime(start, end);
        case Daily:
            return (getDateTime().isBeforeDateTime(end) || getDateTime().equals(end))
                && getDateTime().isBetweenTime(start, end);
        case Weekly:
            return (getDateTime().isBeforeDateTime(end) || getDateTime().equals(end))
                && getDayOfWeek().equals(end.getDayOfWeek())
                && getDateTime().isBetweenTime(start, end);
        default:
            return false;
        }
    }

    @Override
    public boolean isOnDate(LocalDate date) {
        switch (repetition) {
        case Once:
            return super.isOnDate(date);
        case Daily:
            return getDate().isBefore(date) || getDate().equals(date);
        case Weekly:
            return (getDate().isBefore(date) || getDate().equals(date))
                && getDayOfWeek().equals(date.getDayOfWeek());
        default:
            return false;
        }
    }

    /**
     * Returns true if both reminders have the same description, dateTime and repetition.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder.getDescription().equals(getDescription())
            && otherReminder.getDateTime().equals(getDateTime())
            && otherReminder.getRepetition().equals(getRepetition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getDateTime(), getRepetition());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Reminder: ")
            .append(getDescription());
        if (repetition.equals(Repetition.Once)) {
            builder.append(" On ")
                .append(getDateTime());
        } else if (repetition.equals(Repetition.Daily)) {
            builder.append(" At ")
                .append(getTime())
                .append(" everyday")
                .append(" From ")
                .append(getDate());
        } else if (repetition.equals(Repetition.Weekly)) {
            builder.append(" at: ")
                .append(getTime())
                .append(" every ")
                .append(getDayOfWeekString())
                .append(" From ")
                .append(getDate());
        }
        return builder.toString();
    }

    @Override
    public boolean isSameCalendarEntry(CalendarEntry calendarEntry) {
        return calendarEntry instanceof Reminder
            && isSameReminder((Reminder) calendarEntry);
    }
}
