package seedu.address.calendar.model;

import java.util.Optional;

public abstract class Event {
    protected Date date;
    protected Name name;
    protected Optional<Info> info;
    protected EventType eventType;

    public Event(Date date, Name name, Optional<Info> info, EventType eventType) {
        this.date = date;
        this.name = name;
        this.info = info;
        this.eventType = eventType;
    }

    public abstract EventType getEventType();
}
