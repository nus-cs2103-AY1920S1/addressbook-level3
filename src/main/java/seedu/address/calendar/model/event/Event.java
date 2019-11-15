package seedu.address.calendar.model.event;

import java.util.Arrays;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.util.Interval;

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

    /**
     * Gets a placeholder event. This event has the same start and end date as {@code EventQuery}.
     *
     * @param eventQuery The specified event query
     * @return A placeholder {@code Event} instance which has the same start and end date as the event query
     */
    public static Event getEventPlaceHolder(EventQuery eventQuery) {
        Name name = new Name("Placeholder");
        EventType eventType = null;
        return new Event(name, eventQuery.getStart(), eventQuery.getEnd(), eventType);
    }

    /**
     * Gets an instance of {@code EventQuery} which is representative of {@code this}.
     *
     * @return An instance of {@code EventQuery} which is representative of {@code this}
     */
    public EventQuery asEventQuery() {
        return new EventQuery(startDate, endDate);
    }

    /**
     * Checks whether {@code this} indicates that the user is busy.
     *
     * @return {@code true} if {@code this} indicates that the user is busy
     */
    public boolean isBusy() {
        return eventType.isBusy();
    }

    /**
     * Gets the name of {@code this} as a string.
     *
     * @return The {@code String} representation of {@code this} name.
     */
    public String getNameStr() {
        return name.asString();
    }

    /**
     * Gets the start date of {@code this} as a {@code String}.
     *
     * @return A {@code String} representation of {@code this} start date
     */
    public String getStartDateStr() {
        return startDate.asString();
    }

    /**
     * Gets the end date of {@code this} as a {@code String}.
     *
     * @return A {@code String} representation of {@code this} end date
     */
    public String getEndDateStr() {
        return endDate.asString();
    }

    /**
     * Gets the event type of {@code this} as a formatted {@code String}.
     *
     * @return The event type of {@code this} as a formatted {@code String}
     */
    public String getEventTypeStr() {
        return eventType.toString();
    }

    /**
     * Gets the event type of {@code this}.
     *
     * @return The event type of {@code this}
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Checks whether {@code this} is a one day event.
     *
     * @return {@code true} if it is a one day event
     */
    boolean isOneDay() {
        return startDate.equals(endDate);
    }

    /**
     * Checks if {@code this} is the same event as {@code event}.
     *
     * @param event The event to check against
     * @return {@code true} only if {@code this} and {@code event} have the same start and end dates, and the same name
     */
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
