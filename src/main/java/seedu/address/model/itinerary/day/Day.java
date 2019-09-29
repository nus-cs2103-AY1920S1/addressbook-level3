package seedu.address.model.itinerary.day;

import seedu.address.model.itinerary.event.EventList;

public class Day {
    private final EventList eventList;

    public Day(EventList eventList){
        this.eventList = eventList;
    }
}
