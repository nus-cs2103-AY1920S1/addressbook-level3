package seedu.billboard.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX = "The expense index provided is invalid";
    public static final String MESSAGE_EXPENSES_LISTED_OVERVIEW = "%1$d expense(s) listed!";
    public static final String MESSAGE_NONEXISTENT_ARCHIVE_ENTERED =
            "There is no existing archive of the name provided!";
    public static final String MESSAGE_INVALID_ARCHIVE_NAME = "The archive name cannot be empty!";
    public static final String MESSAGE_NOT_UNDOABLE = "There is no command to be undone.";
    public static final String MESSAGE_NOT_REDOABLE = "There is no command to be redone.";
}
