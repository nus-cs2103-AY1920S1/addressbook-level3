package seedu.address.itinerary.model;

import seedu.address.itinerary.model.event.Event;

import java.util.List;

public class ReadOnlyItinerary {
    List<Event> events;

    public ReadOnlyItinerary(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEventList() {
        return List.copyOf(events);
    }
}
