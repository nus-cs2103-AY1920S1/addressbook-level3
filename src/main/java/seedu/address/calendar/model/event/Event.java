package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.util.Interval;

import java.util.Arrays;
import java.util.Optional;

public class Event extends Interval<Date, Event> {
    protected Date startDate;
    protected Date endDate;
    protected Name name;
    protected Optional<Info> info;
    protected EventType eventType;

    public Event(Name name, Date startDate, Date endDate, Optional<Info> info, EventType eventType) {
        super(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.info = info;
        this.eventType = eventType;
    }

    public Event(Name name, Date date, Optional<Info> info, EventType eventType) {
        this(name, date, date, info, eventType);
    }

    public Event(Name name, Date date, EventType eventType) {
        this(name, date, date, Optional.empty(), eventType);
    }

    public boolean isBusy() {
        return eventType.isBusy();
    }

    public String getNameStr() {
        return name.asString();
    }

    public String getStartDateStr() {
        return startDate.asString();
    }

    public String getEndDateStr() {
        return endDate.asString();
    }

    public String getInfoStr() {
        return info.map(i -> i.asString())
                .orElse("");
    }

    public String getEventTypeStr() {
        return eventType.toString();
    }

    public EventType getEventType() {
        return eventType;
    }

    boolean isOneDay() {
        return startDate.equals(endDate);
    }

    @Override
    public int hashCode() {
        Object[] infoArr = {startDate, endDate};
        return Arrays.hashCode(infoArr);
    }
}
