package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;

/**
 * Represents a trip.
 */
public class Trip extends Event {
    private static final EventType EVENT_TYPE = EventType.TRIP;

    /**
     * Constructs a {@code Trip} instance with the specified name, start date and end date.
     *
     * @param name The specified name
     * @param startDate The specified start date
     * @param endDate The specified end date
     */
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
