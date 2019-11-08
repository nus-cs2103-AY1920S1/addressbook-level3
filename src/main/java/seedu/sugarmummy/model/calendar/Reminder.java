package seedu.sugarmummy.model.calendar;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Objects;

import seedu.sugarmummy.model.time.DateTime;

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
     * Returns a Reminder object that represents the current reminder on a given date.
     */
    public Reminder getOneTimeReminderOn(LocalDate date) {
        assert isOnDate(date);
        return new Reminder(getDescription(), new DateTime(date, getTime()), Repetition.Once);
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
                && otherReminder.getTime().equals(getTime());
    }

    /**
     * Returns true if the reminder is a subset of the given reminder.
     * A reminder is considered to be a subset if the given reminder will occur whenever it occur.
     */
    public boolean isSubsetOf(Reminder otherReminder) {
        requireNonNull(otherReminder);
        if (!isSameReminder(otherReminder)) {
            return false;
        }
        switch (repetition) {
        case Once:
            return otherReminder.isOnDate(getDate());
        case Daily:
            switch (otherReminder.getRepetition()) {
            case Daily:
                return otherReminder.isOnDate(getDate());
            default:
                return false;
            }
        case Weekly:
            switch (otherReminder.getRepetition()) {
            case Daily:
            case Weekly:
                return otherReminder.isOnDate(getDate());
            default:
                return false;
            }
        default:
            return false;
        }
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
        builder.append(getDescription());
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
                && equals(calendarEntry);
    }

    @Override
    public boolean isSubsetCalendarEntryOf(CalendarEntry calendarEntry) {
        if (calendarEntry == null) {
            return false;
        }
        if (calendarEntry instanceof Reminder) {
            return isSubsetOf((Reminder) calendarEntry);
        } else {
            return false;
        }
    }

    /**
     * Returns true if the reminder overlaps the given reminder.
     * Two reminders are considered overlapping if they will occur on the same day, at the same day and
     * with the same description.
     */
    public boolean overlaps(Reminder otherReminder) {
        requireNonNull(otherReminder);
        if (!isSameReminder(otherReminder)) {
            return false;
        }
        switch (repetition) {
        case Once:
            return otherReminder.isOnDate(getDate());
        case Daily:
            switch (otherReminder.getRepetition()) {
            case Once:
                return this.isOnDate(otherReminder.getDate());
            case Weekly:
            case Daily:
                return true;
            default:
                return false;
            }
        case Weekly:
            switch (otherReminder.getRepetition()) {
            case Once:
                return this.isOnDate(otherReminder.getDate());
            case Daily:
                return true;
            case Weekly:
                return this.getDayOfWeek().equals(otherReminder.getDayOfWeek());
            default:
                return false;
            }
        default:
            return false;
        }
    }

    @Override
    public boolean overlaps(CalendarEntry calendarEntry) {
        if (calendarEntry == null || !(calendarEntry instanceof Reminder)) {
            return false;
        }

        return overlaps((Reminder) calendarEntry);
    }

    @Override
    public boolean conflicts(CalendarEntry calendarEntry) {
        if (calendarEntry == null || !(calendarEntry instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) calendarEntry;
        return overlaps(otherReminder) && !isSubsetOf(otherReminder) && !otherReminder.isSubsetOf(this);
    }
}
