package seedu.address.model.person.exceptions;

import seedu.address.model.person.schedule.Event;

/**
 * Represents an error when an event clash is encountered.
 */
public class EventClashException extends Exception {

    public EventClashException(Event event) {
        super("Clash in event: " + event.getEventName());
    }

}
