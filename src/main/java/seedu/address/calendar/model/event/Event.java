package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.util.Interval;

import java.util.Optional;

public abstract class Event extends Interval<Date, Event> {
    protected Date startDate;
    protected Date endDate = null; // todo: update
    protected Name name;
    protected Optional<Info> info;
    protected EventType eventType;

    public Event(Name name, Date startDate, Optional<Info> info, EventType eventType) {
        super(startDate, startDate); // todo: update
        this.startDate = startDate;
        this.name = name;
        this.info = info;
        this.eventType = eventType;
    }

    public abstract EventType getEventType();

    public String getNameStr() {
        return name.asString();
    }

    public String getStartDateStr() {
        return startDate.asString();
    }

    public String getEndDateStr() {
        return startDate.asString(); // todo: change to end date
    }

    public String getInfoStr() {
        return info.map(i -> i.asString())
                .orElse("");
    }
}
