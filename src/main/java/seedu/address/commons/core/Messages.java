package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX = "The employee index provided is invalid";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d events listed!";
    public static final String MESSAGE_UNAVAILABLE_MANPOWER = "Unavailable manpower due to event schedule overlap";
    public static final String MESSAGE_MANPOWER_COUNT_EXCEEDED = "Manpower count to allocate "
            + "exceeds manpower needed by event!";
    public static final String MESSAGE_INSUFFICIENT_MANPOWER_COUNT = "Insufficient manpower count for event! "
            + "(possibly due to schedule overlap)";
}
