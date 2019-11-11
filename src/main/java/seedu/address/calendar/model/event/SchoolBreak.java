package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;

/**
 * Represents a school break.
 */
public class SchoolBreak extends Event {
    private static final EventType EVENT_TYPE = EventType.SCHOOL_BREAK;

    /**
     * Constructs a {@code SchoolBreak} instance with the specified name, start date and end date.
     *
     * @param name The specified name
     * @param startDate The specified start date
     * @param endDate The specified end date
     */
    public SchoolBreak(Name name, Date startDate, Date endDate) {
        super(name, startDate, endDate, EVENT_TYPE);
    }

    @Override
    public String toString() {
        if (isOneDay()) {
            return String.format("'%s' school break on %s", name, startDate);
        }
        return String.format("'%s' school break from %s to %s", name, startDate, endDate);
    }
}
