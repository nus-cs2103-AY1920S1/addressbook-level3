package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_UNKNOWN_TYPE = "Unknown entity type";
    public static final String MESSAGE_INCOMPLETE_INPUT = "User input is incomplete";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MISSING_TIEBREAK_METHODS = "Tie-break methods must be specified if "
            + "tie-break prefix \"tb/\" is typed.";
    public static final String MESSAGE_INVALID_TIE_BREAK = "The tie-break method specified does not exist or is not "
            + "supported by Alfred: ";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The entity ID provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    // to set restrictions as some operations can only be applied to some entity
    public static final String MESSAGE_INVALID_TYPE = "The type of entity is invalid";
    public static final String MESSAGE_INVALID_INDEX = "The specified ID is preceded by an invalid prefix "
            + "or is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX = "The team ID provided is of an invalid format.";
    public static final String MESSAGE_NON_EXISTENT_TEAM = "The Team with the specified ID does not exist.";
    public static final String MESSAGE_NON_EXISTENT_MENTOR = "The Mentor with the specified ID does not exist.";
    public static final String MESSAGE_NON_EXISTENT_PARTICIPANT = "The Participant with the specified ID"
            + " does not exist.";

}
