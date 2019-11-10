package seedu.moolah.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX = "The expense index provided is invalid";
    public static final String MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX = "The budget index provided is invalid";
    public static final String MESSAGE_BUDGET_NOT_FOUND = "This budget does not exist in MooLah";
    public static final String MESSAGE_CANNOT_DELETE_DEFAULT_BUDGET = "Default budget cannot be deleted";
    public static final String MESSAGE_EXPENSES_LISTED_OVERVIEW = "%1$d expenses listed!";
    public static final String MESSAGE_REPEATED_PREFIX_COMMAND = "Repeated prefixes are not allowed!";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_DISPLAY_STATISTICS_WITHOUT_BUDGET = "Statistics without budgets is not allowed";
    public static final String MESSAGE_CONSTRAINTS_END_DATE = "Start date must be before end date.";
    public static final String MESSAGE_EVENT_WITH_PAST_TIMESTAMP = "An event must occur in the future!";
    public static final String MESSAGE_EXPENSE_WITH_FUTURE_TIMESTAMP =
            "An expense cannot be in the future! Add an event instead.";

}
