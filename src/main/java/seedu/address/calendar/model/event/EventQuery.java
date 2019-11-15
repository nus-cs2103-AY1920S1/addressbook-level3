package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.util.Interval;

/**
 * Represents an event query which has only start and end dates.
 */
public class EventQuery extends Interval<Date, EventQuery> {
    protected Date startDate;
    protected Date endDate;

    /**
     * Creates an {@code EventQuery} instance with the specified start and end dates.
     * @param startDate The specified start date
     * @param endDate The specified end date
     */
    public EventQuery(Date startDate, Date endDate) {
        super(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Checks if the specified start and end date would make a valid query/event.
     * @param startDate The specified start date
     * @param endDate The specified end date
     * @return {@code true} if the start date is not after the end date
     */
    public static boolean isValidEventTime(Date startDate, Date endDate) {
        return startDate.compareTo(endDate) <= 0;
    }

    /**
     * Checks whether the other {@code EventQuery} instance has the same start and end date as {@code this}.
     *
     * @param otherEventQuery The other event query to check against
     * @return {@code true} if the two instances have the same start and end dates
     */
    boolean isSameInterval(EventQuery otherEventQuery) {
        Date otherStartDate = otherEventQuery.startDate;
        Date otherEndDate = otherEventQuery.endDate;
        return startDate.equals(otherStartDate) && endDate.equals(otherEndDate);
    }

    @Override
    public String toString() {
        boolean isOneDayLong = startDate.equals(endDate);
        if (isOneDayLong) {
            return String.format("On %s", startDate);
        }
        return String.format("From %s to %s", startDate, endDate);
    }
}
