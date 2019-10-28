package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ARGUMENT_FORMAT = "Invalid arguments! %1$s";
    public static final String MESSAGE_INVALID_CATEGORY = "The category provided is invalid. "
                                                            + "Create the category using addCategory.";
    public static final String MESSAGE_NONEXISTENT_CATEGORY = "The category modified is non-existent";
    public static final String MESSAGE_EXISTING_ENTRIES_CATEGORY = "The category deleted has existing entries. "
                                                            + "Delete those entries first.";
    public static final String MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX = "The entry index provided is invalid";
    public static final String MESSAGE_ENTRIES_LISTED_OVERVIEW = "%1$d entries listed!";
    public static final String MESSAGE_WISHES_LISTED_OVERVIEW = "%1$d wishes listed!";
    public static final String MESSAGE_BUDGETS_LISTED_OVERVIEW = "%1$d budgets listed!";
}
