package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;

import java.util.Optional;

public class Trip extends Event {
    private static final EventType EVENT_TYPE = EventType.TRIP;

    public Trip(Name name, Date startDate, Date endDate, Optional<Info> info) {
        super(name, startDate, endDate, info, EVENT_TYPE);
    }

    public Trip(Name name, Date startDate, Date endDate) {
        super(name, startDate, endDate, EVENT_TYPE);
    }

    @Override
    public String toString() {
        if (isOneDay()) {
            return String.format("'%s' trip on %s", name, startDate);
        }
        return String.format("'%s' trip from %s to %s", name, startDate, endDate);
    }
}
