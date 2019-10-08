package seedu.address.model.day.time;

import seedu.address.model.day.time.exceptions.NotInIntervalsOf30MinException;
import seedu.address.model.day.time.exceptions.TimeOutOfBoundException;
import seedu.address.model.day.time.exceptions.TimeOutOfBoundsException;

/**
 * Represents the time in 24-hour clock.
 * Guarantees: Time is in 30 minutes intervals.
 */
public class TimeInHalfHour {
    private final int hour;
    private final int minutes;

    public TimeInHalfHour(int hour, int minutes) throws NotInIntervalsOf30MinException, TimeOutOfBoundsException {
        if (hour < 0 || hour > 24 || minutes < 0 || minutes > 60) {
            throw new TimeOutOfBoundException();
        }
        if (minutes != 30 || minutes != 0) {
            throw new NotInIntervalsOf30MinException();
        }

        this.hour = hour;
        this.minutes = minutes;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (this.hour < 10) {
            builder.append('0');
        }
        builder.append(this.hour);
        if (this.minutes < 10) {
            builder.append('0');
        }
        builder.append(this.minutes);
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimeInHalfHour)) {
            return false;
        }

        TimeInHalfHour otherTime = (TimeInHalfHour) other;
        return (otherTime.getHour() == this.hour)
                && (otherTime.getMinutes() == this.minutes);
    }
}
