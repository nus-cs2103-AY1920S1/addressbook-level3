package seedu.address.model.events;

import java.time.Instant;

/**
 * Represents a date and time.
 * Internally stored as seconds from epoch, without any timezone information.
 */
public class DateTime {

    private final Instant dateTime;

    public DateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Instant getDateTime() {
        return this.dateTime;
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }
}
