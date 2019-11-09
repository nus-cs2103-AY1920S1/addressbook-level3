package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.util.Interval;

import java.util.Arrays;
import java.util.Optional;

/**
 * Represents an event. It contains information about the event's start and end dates, name and event type.
 * Guarantees: It cannot be instantiated directly.
 */
public class Event extends Interval<Date, Event> {
    protected Date startDate;
    protected Date endDate;
    protected Name name;
    protected EventType eventType;

    /**
     * Creates an event with the specified name, start date, end date and event type.
     *
     * @param name The specified name of the event
     * @param startDate The specified start date of the event
     * @param endDate The specified end date of the event
     * @param eventType The type of event
     */

    protected Event(Name name, Date startDate, Date endDate, EventType eventType) {
        super(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.eventType = eventType;
    }

    public static Event getEventPlaceHolder(EventQuery eventQuery) {
        Name name = new Name("");
        EventType eventType = null;
        return new Event(name, eventQuery.getStart(), eventQuery.getEnd(), eventType);
    }

    public static EventQuery asEventQuery(Event event) {
        Date startDate = event.getStart();
        Date endDate = event.getEnd();
        return new EventQuery(startDate, endDate);
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

    public String getEventTypeStr() {
        return eventType.toString();
    }

    public EventType getEventType() {
        return eventType;
    }

    boolean isOneDay() {
        return startDate.equals(endDate);
    }

    boolean isIdentical(Event event) {
        boolean isSameDates = this.equals(event);
        boolean isSameName = name.equals(event.name);
        boolean isSameEventType = eventType.equals(event.eventType);

        return isSameDates && isSameName && isSameEventType;
    }

    @Override
    public int hashCode() {
        Object[] infoArr = {startDate, endDate};
        return Arrays.hashCode(infoArr);
    }
}
