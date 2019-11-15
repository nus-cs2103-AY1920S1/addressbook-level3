package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;

/**
 * Represents a commitment.
 */
public class Commitment extends Event {
    private static final EventType EVENT_TYPE = EventType.COMMITMENT;

    /**
     * Constructs a {@code Commitment} instance with the specified name, start date and end date.
     *
     * @param name The specified name
     * @param startDate The specified start date
     * @param endDate The specified end date
     */
    public Commitment(Name name, Date startDate, Date endDate) {
        super(name, startDate, endDate, EVENT_TYPE);
    }

    @Override
    public String toString() {
        if (isOneDay()) {
            return String.format("'%s' commitment on %s", name, startDate);
        }
        return String.format("'%s' commitment from %s to %s", name, startDate, endDate);
    }
}
