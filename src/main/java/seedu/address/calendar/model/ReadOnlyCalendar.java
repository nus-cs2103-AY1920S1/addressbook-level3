package seedu.address.calendar.model;

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
