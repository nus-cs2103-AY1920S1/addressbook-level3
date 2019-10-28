package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;

import java.util.Optional;

public class Holiday extends Event {
    private static final EventType EVENT_TYPE = EventType.HOLIDAY;

    public Holiday(Name name, Date startDate, Date endDate, Optional<Info> info) {
        super(name, startDate, endDate, info, EVENT_TYPE);
    }

    public Holiday(Name name, Date startDate, Date endDate) {
        super(name, startDate, endDate, EVENT_TYPE);
    }

    @Override
    public String toString() {
        if (isOneDay()) {
            return String.format("'%s' holiday on %s", name, startDate);
        }
        return String.format("'%s' holiday from %s to %s", name, startDate, endDate);
    }
}
