package seedu.sugarmummy.model.calendar;

import java.util.Optional;

import seedu.sugarmummy.model.time.DateTime;

/**
 * Represents an Event in the calendar.
 */
public class Event extends CalendarEntry {
    private Optional<DateTime> endingDateTime;
    private Optional<Reminder> autoReminder;

    public Event(Description description, DateTime dateTime) {
        super(description, dateTime);
        endingDateTime = Optional.empty();
        autoReminder = Optional.empty();
    }

    public Optional<DateTime> getEndingDateTime() {
        return endingDateTime;
    }

    public void setEndingDateTime(Optional<DateTime> endingDateTime) {
        this.endingDateTime = endingDateTime;
    }

    public void setEndingDateTime(DateTime endingDateTime) {
        this.endingDateTime = Optional.ofNullable(endingDateTime);
    }

    public Optional<Reminder> getAutoReminder() {
        return autoReminder;
    }

    public void setAutoReminder(Optional<Reminder> autoReminder) {
        this.autoReminder = autoReminder;
    }

    public void setAutoReminder(Reminder reminder) {
        this.autoReminder = Optional.ofNullable(reminder);
    }

    /**
     * Returns true if both events have the same description, date and time; This defines a weaker notion of equality
     * between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getDescription().equals(getDescription())
                && otherEvent.getDateTime().equals(getDateTime());
    }


    @Override
    public boolean isSameCalendarEntry(CalendarEntry calendarEntry) {
        return calendarEntry instanceof Event
                && isSameEvent((Event) calendarEntry);
    }

    @Override
    public boolean isSubsetCalendarEntryOf(CalendarEntry calendarEntry) {
        return false;
    }

    @Override
    public boolean overlaps(CalendarEntry calendarEntry) {
        if (calendarEntry == null || !(calendarEntry instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) calendarEntry;
        if (otherEvent.endingDateTime.isPresent()) {
            if (this.endingDateTime.isPresent()) {
                return this.getDateTime().isBetweenDateTime(otherEvent.getDateTime(), otherEvent.endingDateTime.get())
                        || otherEvent.getDateTime().isBetweenDateTime(getDateTime(), getEndingDateTime().get());
            } else {
                return this.getDateTime().isBetweenDateTime(otherEvent.getDateTime(), otherEvent.endingDateTime.get());
            }
        } else if (this.endingDateTime.isPresent()) {
            return otherEvent.getDateTime().isBetweenDateTime(this.getDateTime(), this.endingDateTime.get());
        } else {
            return this.getDateTime().equals(otherEvent.getDateTime());
        }
    }

    @Override
    public boolean conflicts(CalendarEntry calendarEntry) {
        return false;
    }

    @Override
    public boolean isBetween(DateTime start, DateTime end) {
        return getDateTime().isBetweenDateTime(start, end);
    }

    /**
     * Returns true if both events have the same description, dateTime, endingDateTime and autoReminder.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getDescription().equals(getDescription())
                && otherEvent.getDateTime().equals(getDateTime())
                && otherEvent.getEndingDateTime().equals(getEndingDateTime())
                && otherEvent.getAutoReminder().equals(getAutoReminder());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" From ")
                .append(getDateTime())
                .append(getEndingTimeString());
        return builder.toString();
    }

    private String getEndingTimeString() {
        if (endingDateTime.isPresent()) {
            return " To: " + endingDateTime.get();
        } else {
            return "";
        }
    }
}
