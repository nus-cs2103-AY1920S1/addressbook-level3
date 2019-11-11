package seedu.address.calendar.model.event;

import java.util.stream.Stream;

/**
 * Allows an observer to view events found in {@code EventManager}.
 */
public interface EventViewer {
    /**
     * Gets all events that happen during the specified event query.
     * @param eventQuery The specified event query
     * @return All events that happen during the specified event query
     */
    Stream<Event> getEvents(EventQuery eventQuery);
}
