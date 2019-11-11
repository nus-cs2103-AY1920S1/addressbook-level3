package seedu.scheduler.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_NAME = "The person name provided does not exist";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_CRITICAL_ERROR = "Internal Error!";
    public static final String MESSAGE_INVALID_YEAR_OF_STUDY = "Incorrect year of study format! "
            + "The year of study must be a positive integer within the range [1, 5].\n"
            + "Examples:\n"
            + "Correct: 1\n"
            + "Incorrect: -1";
}
