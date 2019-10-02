package seedu.address.model.itinerary.day;

import seedu.address.model.itinerary.event.EventList;

public class Day {
    private final EventList eventList;

    public Day(EventList eventList){
        this.eventList = eventList;
    }

    public boolean isSameDay(Day day) {
        return false;
    }

    public boolean isClashingWith(Day editedDay) {
        return false;
    }
}
