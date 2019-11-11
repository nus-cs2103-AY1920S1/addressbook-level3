package seedu.address.calendar.model;

import seedu.address.calendar.model.event.Event;

import java.util.List;

public class ReadOnlyCalendar {
    List<Event> events;

    public ReadOnlyCalendar(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEventList() {
        return List.copyOf(events);
    }
}
