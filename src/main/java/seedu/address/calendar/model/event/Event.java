package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.util.Interval;

import java.util.Optional;

public abstract class Event implements Interval<Date, Event> {
    protected Date startDate;
    protected Date endDate = null; // todo: update
    protected Name name;
    protected Optional<Info> info;
    protected EventType eventType;

    public Event(Name name, Date startDate, Optional<Info> info, EventType eventType) {
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

    @Override
    public boolean isEndsAfter(Date date) {
        // todo: change to endDate
        return endDate.compareTo(date) > 0;
    }

    @Override
    public boolean isStartsAfter(Date date) {
        return startDate.compareTo(date) > 0;
    }

    @Override
    public boolean contains(Date date) {
        boolean isStartBeforeOrAt = startDate.compareTo(date) <= 0;
        boolean isEndsAfterOrAt = endDate.compareTo(date) >= 0;
        return isStartBeforeOrAt && isEndsAfterOrAt;
    }

    @Override
    public Date getStart() {
        return startDate;
    }

    @Override
    public Date getEnd() {
        return endDate;
    }

    @Override
    public int compareTo(Interval<Date, Event> other) {
        Date otherStartDate = other.getStart();
        return startDate.compareTo(otherStartDate);
    }
}
