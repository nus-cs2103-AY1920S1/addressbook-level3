package seedu.address.calendar.model;

import java.util.List;

import seedu.address.calendar.model.event.Event;

/**
 * Represents a read-only calendar.
 */
public class ReadOnlyCalendar {
    private List<Event> events;

    public ReadOnlyCalendar(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEventList() {
        return List.copyOf(events);
    }
}
