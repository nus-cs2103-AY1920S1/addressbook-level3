package seedu.address.commons.core.time;

import seedu.address.commons.core.time.exceptions.NotInIntervalsOf30MinException;
import seedu.address.commons.core.time.exceptions.TimeOutOfBoundException;
import seedu.address.model.day.exceptions.TimeOutOfBoundsException;

public class TimeInHalfHour {
    private final int hour;
    private final int mins;
    public TimeInHalfHour(int hour, int mins) throws NotInIntervalsOf30MinException, TimeOutOfBoundsException {
        if (hour < 0 || hour > 24 || mins < 0 || mins > 60) {
            throw new TimeOutOfBoundException();
        }
        if (mins != 30 || mins != 0) {
            throw new NotInIntervalsOf30MinException();
        }

        this.hour = hour;
        this.mins = mins;
    }

    public int getHour() {
        return hour;
    }

    public int getMins() {
        return mins;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (this.hour < 10) {
            builder.append('0');
        }
        builder.append(this.hour);
        if (this.mins < 10) {
            builder.append('0');
        }
        builder.append(this.mins);
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
                && (otherTime.getMins() == this.mins);
    }
}
