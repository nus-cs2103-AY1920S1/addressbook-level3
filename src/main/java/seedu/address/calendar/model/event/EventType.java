package seedu.address.calendar.model.event;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the various valid event types (i.e. commitment, trip, school break and holiday).
 */
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

    /**
     * Checks whether {@code this} implies that the user is not available.
     *
     * @return {@code true} if the {@code EventType} is {@code COMMITMENT} or is {@code TRIP}
     */
    boolean isBusy() {
        return isBusy;
    }

    /**
     * Converts a representative {@code String} of {@code EventType} to an instance of {@code EventType}.
     * Note: The conversion is not case-sensitive and ignores any trailing spaces
     *
     * @param eventType Representative {@code String} of an {@code EventType}
     * @return {@code EventType} that is represented by {@code eventType}
     * @throws IllegalValueException if {@code eventType} does not represent any valid {@code EventType}
     * @throws NullPointerException if {@code eventType} is {@code null}
     */
    public static EventType getInstanceFromString(String eventType) throws IllegalValueException {
        try {
            EventType requiredEventType = EventType.valueOf(eventType.trim().toUpperCase());
            return requiredEventType;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }
    }
}
