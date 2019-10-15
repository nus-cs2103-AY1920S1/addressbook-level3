package seedu.address.model.events;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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

    /**
     * Creates a new instance of DateTime according to the clock.
     *
     * @return A new instance of DateTime according to the clock.
     */
    public static DateTime now() {
        return new DateTime(Instant.now());
    }

    /**
     * Computes the number of milliseconds between the calling instance of DateTime
     *     and the argument instance of Time. If the argument instance of DateTime
     *     occurs before the calling instance, the result returned will be negative.
     *
     * @param futureTime A DateTime that does not have (but is expected) to be in the future.
     * @return The number of milliseconds between the calling instance of DateTime
     *     and the argument instance of DateTime.
     */
    public long msecsTimeUntil(DateTime futureTime) {
        return futureTime.dateTime.toEpochMilli() - this.dateTime.toEpochMilli();
    }

    /**
     * Checks if two instances of DateTime are equal up to the current minute.
     *
     * @param other The DateTime to be compared to
     * @return <code> true </code> only if both this instance and the other instance of DateTime are equal
     *     up to the current minute, but not any more precise than that.
     */
    public boolean equalsPrecisionMinute(DateTime other) {
        return this.dateTime.truncatedTo(ChronoUnit.MINUTES)
                .equals(other.dateTime.truncatedTo(ChronoUnit.MINUTES));
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }
}
