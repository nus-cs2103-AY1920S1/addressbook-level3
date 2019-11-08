package seedu.address.model.events;

import java.util.Comparator;

//@@author Kyzure
/**
 * Represents a Comparator to compare the events by their date, followed by their name in lexicographic order.
 */
public class EventDateComparator implements Comparator<EventSource> {

    @Override
    public int compare(EventSource event, EventSource otherEvent) {
        if (event.getStartDateTime().compareTo(otherEvent.getStartDateTime()) == 0) {
            return event.getDescription().compareTo(otherEvent.getDescription());
        } else {
            return event.getStartDateTime().compareTo(otherEvent.getStartDateTime());
        }
    }
}
