package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;

import java.util.Optional;

public abstract class Event {
    protected Date date;
    protected Name name;
    protected Optional<Info> info;
    protected EventType eventType;

    public Event(Name name, Date date, Optional<Info> info, EventType eventType) {
        this.date = date;
        this.name = name;
        this.info = info;
        this.eventType = eventType;
    }

    public abstract EventType getEventType();

    public String getNameStr() {
        return name.asString();
    }

    public String getStartDateStr() {
        return date.asString();
    }

    public String getEndDateStr() {
        return date.asString(); // todo: change to end date
    }

    public String getInfoStr() {
        return info.map(i -> i.asString())
                .orElse("");
    }
}
