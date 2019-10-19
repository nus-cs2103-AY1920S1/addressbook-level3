package seedu.address.model.person.exceptions;

import seedu.address.model.person.schedule.Event;

public class EventClashException extends Exception {

    public EventClashException(Event event) {
        super("Clash in event: " + event.getEventName());
    }

}
