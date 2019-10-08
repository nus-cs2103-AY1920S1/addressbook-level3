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

    @Override
    public String toString() {
        return dateTime.toString();
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
}
