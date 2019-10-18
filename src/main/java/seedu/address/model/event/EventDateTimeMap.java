package seedu.address.model.event;

import java.util.HashMap;

/**
 * Represents a HashMap of the EventDate to EventDayTime.
 * Used to represent the time period that the event is hosted for throughout a single day.
 */
public class EventDateTimeMap {
    private final HashMap<EventDate, EventDayTime> dateTimeMap;

    public EventDateTimeMap() {
        this.dateTimeMap = new HashMap<>();
    }

    public HashMap<EventDate, EventDayTime> getDateTimeMap() {
        return dateTimeMap;
    }

    /**
     * Add/Update the Key-Value pair of an EventDate to a EventDayTime
     * @param date EventDate object representing a Date of an Event
     * @param dayTime EventDayTime object representing the time period in a day that the event is hosted
     */
    public void mapDateTime(EventDate date, EventDayTime dayTime) {
        dateTimeMap.put(date, dayTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDateTimeMap // instanceof handles nulls
                && dateTimeMap.equals(((EventDateTimeMap) other).getDateTimeMap())); // state check
    }

    @Override
    public int hashCode() {
        return dateTimeMap.hashCode();
    }
}
