package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format or missing fields! \n%1$s";
    public static final String MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX = "The question index provided is invalid";
    public static final String MESSAGE_INVALID_LOG_ENTRY_DISPLAYED_INDEX = "The log entry index provided is invalid";
    public static final String MESSAGE_INVALID_MODULE_DISPLAYED_INDEX = "The given index is not valid.";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_QUESTIONS_LISTED_OVERVIEW = "%1$d questions listed!";
    public static final String MESSAGE_LOG_ENTRIES_LISTED_OVERVIEW = "%1$d log entries listed!";
    public static final String MESSAGE_STATE_CHANGE = "Application switched";
    public static final String MESSAGE_SWITCH_INVALID = "Switch parameter only accepts quiz, finance, calendar and cap";

    //////////CAP//////////////
    public static final String MESSAGE_MODULES_LISTED_OVERVIEW = "found a match in %1$d of the module(s)!";
    public static final String MESSAGE_GAIN_RANKING = "Congratulations, you have attained %1$d!";
    public static final String MESSAGE_LOST_RANKING = "You have dropped a class.";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "The module you wish to delete does not exist.";
}
