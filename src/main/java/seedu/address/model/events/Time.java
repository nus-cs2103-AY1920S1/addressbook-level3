package seedu.address.model.events;

import java.time.Instant;

public class Time {

    private final Instant dateTime;

    public Time(Instant dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }
}
