package seedu.address.commons.stub;

import java.time.Instant;

public class TimeStub {
    private final Instant dateTime;

    public TimeStub(Instant dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TimeStub
                && dateTime.equals(((TimeStub) other).dateTime));
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

}