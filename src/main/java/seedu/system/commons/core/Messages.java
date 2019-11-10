package seedu.system.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_COMPETITION_DISPLAYED_INDEX = "The competition index provided is "
            + "invalid";
    public static final String MESSAGE_INVALID_PARTICIPATION_DISPLAYED_INDEX = "The competition index provided is "
            + "invalid";
    public static final String MESSAGE_INVALID_START_END_DATES = "The start date is after end date.";
    public static final String MESSAGE_COMPETITIONS_LISTED_OVERVIEW = "%1$d competitions listed!";
    public static final String MESSAGE_PARTICIPATIONS_LISTED_OVERVIEW = "%1$d participations listed!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_PERSONS_DOB = "Person must be born already!";
    public static final String MESSAGE_NO_ONGOING_COMPETITION_SESSION = "There is no ongoing competition session."
            + " Please start one before making commands.";
    public static final String MESSAGE_INVALID_NEGATIVE_ATTEMPT_WEIGHT = "Negative attempt weights are not allowed.";

}
