package seedu.address.calendar.model.event;

import seedu.address.commons.exceptions.IllegalValueException;

public enum EventType {
    COMMITMENT(true),
    HOLIDAY(false),
    SCHOOL_BREAK(false),
    TRIP(true);

    public static final String MESSAGE_CONSTRAINTS = "Event type must be 'commitment', 'holiday', 'school_break'"
            + "or 'trip'. Note that it is not case sensitive.";

    private boolean isBusy;

    EventType(boolean isBusy) {
        this.isBusy = isBusy;
    }

    boolean isBusy() {
        return isBusy;
    }

    public static EventType getInstanceFromString(String eventType) throws IllegalValueException {
        try {
            EventType requiredEventType = EventType.valueOf(eventType);
            return requiredEventType;
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }
    }
}
