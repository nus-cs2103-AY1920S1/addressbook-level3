package budgetbuddy.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_DISPLAYED_INDEX = "The index provided is invalid";

    public static final String MESSAGE_TRANSACTIONS_LISTED_OVERVIEW = "%1$d transactions listed!";
    public static final String MESSAGE_ACCOUNTS_LISTED_OVERVIEW = "%1$d account(s) listed!";
    public static final String MESSAGE_ACCOUNT_SEARCH_RESULTS_EMPTY = "There are no results matching your search,"
            + " listing all %1$d account(s)   ";
    public static final String MESSAGE_ACCOUNT_NOT_FOUND = "The account \"%1$s\" could not be found";
    public static final String MESSAGE_LAST_ACCOUNT_DELETION_ILLEGAL = "The last account cannot be deleted!";

    public static final String MESSAGE_NO_SUCH_SCRIPT = "Could not find a script named \"%1$s\".";
}
