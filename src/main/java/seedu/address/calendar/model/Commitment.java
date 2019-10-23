package seedu.address.calendar.model;

import java.util.Optional;

public class Commitment extends Event {
    private static final EventType EVENT_TYPE = EventType.COMMITMENT;

    public Commitment(Name name, Date date, Optional<Info> info) {
        super(name, date, info, EVENT_TYPE);
    }

    public EventType getEventType() {
        return EVENT_TYPE;
    }

    @Override
    public String toString() {
        return String.format("%s commitment on %s", name, date);
    }
}
