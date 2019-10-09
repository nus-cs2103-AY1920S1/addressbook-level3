package seedu.address.model.calendar;

import java.util.Objects;

/**
 * Represents a Reminder in the calendar.
 */
public class Reminder extends CalendarEntry{

    private Repetition repetition;

    public Reminder(Description description, DateTime dateTime, Repetition repetition) {
        super(description, dateTime);
        this.repetition = repetition;
    }

    public Repetition getRepetition() {
        return repetition;
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
        builder.append("Reminder")
                .append(" Description: ")
                .append(getDescription());
        if(repetition.equals(Repetition.Once)) {
            builder.append(" on: ")
                    .append(getDateTime());
        } else if(repetition.equals(Repetition.EveryDay)){
            builder.append(" at: ")
                    .append(getTime())
                    .append(" everyday")
                    .append(" from: ")
                    .append(getDate());
        } else if(repetition.equals(Repetition.EveryWeek)){
            builder.append(" at: ")
                    .append(getTime())
                    .append(" every ")
                    .append(getDayOfWeekString());
        }
        return builder.toString();
    }
}
