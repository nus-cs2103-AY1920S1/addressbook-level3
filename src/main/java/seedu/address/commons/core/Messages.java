package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TIMING_COMPARE_NOW = "Timing should be after current time \n%1$s";
    public static final String MESSAGE_INVALID_TIMING_COMPARE_END = "The endTiming should be after startTiming \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The index provided is invalid";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person '%1$s' is not registered";
    public static final String MESSAGE_INVALID_REFERENCEID = "the reference id is not belong to any patient";
    public static final String MESSAGE_INVALID_ROOM = "the room does not exist";
    public static final String MESSAGE_INVAILD_REFERENCE_ID =
            "The reference ID '%1$s' does not belong to any registered person!";
    public static final String MESSAGE_INVALID_APPOINTMENT_DATE_TIME = "The appointment dateTime provided is invalid!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_ALL_EVENTS_LISTED_OVERVIEW = "%1$d appointments listed!";
    public static final String MESSAGE_MISSED_EVENT_LISTED_OVERVIEW = "%1$d missing appointmennt need to settle!";
    public static final String MESSAGE_MISSED_EVENTS_LISTED_OVERVIEW = "%1$d missing appointmennts need to settle!";
    public static final String MESSAGE_INVALID_ROOM_INDEX = "Invalid room index provided!";
    public static final String MESSAGE_INVALID_INDEX = "The appointment index provided is not correct";
    public static final String MESSAGE_NOT_PATIENTLIST = "displaying appointment are not belong to the same patient\n";
    public static final String MESSAGE_NOT_MISSEDLIST = "current displaying appointment are not "
            + "belong to missed appointment list\n" + "please type command: missedappt";
    public static final String MESSAGE_NOTHING_SETTLE = "no missed appointment needs to settle";
    public static final String MESSAGE_EVENT_NOT_FOUND = "The appointment '%1$s' is not registered";
}
