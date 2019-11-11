package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    // Command messages
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_ALL_FLAG_CONSTRAINTS =
            "There should not be any arguments before the -all flag!";
    public static final String MESSAGE_INVALID_DISPLAY_LIMIT = "Display limit must be a positive integer!";
    public static final String MESSAGE_UNUSED_ARGUMENT = "\n\'%1$s\' is ignored as \'%2$s\'"
            + " does not accept arguments.";
    public static final String MESSAGE_CANNOT_EXIT_FROM_SERVE_MODE =
            "Please finish serving the current borrower using 'done' before exiting!";

    // Book messages
    public static final String MESSAGE_DUPLICATE_BOOK = "Serial number provided is already in use!";
    public static final String MESSAGE_INVALID_BOOK_DISPLAYED_INDEX = "The book index provided is invalid!";
    public static final String MESSAGE_BOOKS_LISTED_OVERVIEW = "%1$d books listed!";
    public static final String MESSAGE_NO_SUCH_BOOK = "No such book!";
    public static final String MESSAGE_GENRE_TOO_LONG = "Genre name should not be more than %1$s characters!";
    public static final String MESSAGE_BOOK_TITLE_TOO_LONG = "Title of book should not be more than %1$s characters!";
    public static final String MESSAGE_AUTHOR_NAME_TOO_LONG = "Name of author should not be more than 30 characters!";
    public static final String MESSAGE_TOO_MANY_GENRES =
            "Too many genres specified! The maximum number of genres is %1$d.";

    // Serial Number messages
    public static final String MESSAGE_INVALID_SERIAL_NUMBER = "Invalid Serial Number! \n%1$s";

    // Borrower messages
    public static final String MESSAGE_DUPLICATE_BORROWER = "Phone/Email is already in used! ";
    public static final String MESSAGE_NO_SUCH_BORROWER_ID = "No such borrower ID!";
    public static final String MESSAGE_NOT_IN_SERVE_MODE = "Not in Serve mode! Enter Serve mode to use this command!";
    public static final String MESSAGE_CANNOT_UNREGISTER_WHILE_SERVING =
            "Cannot unregister currently served borrower!\nPlease exit serve mode before unregistering %1$s.";
    public static final String MESSAGE_CANNOT_UNREGISTER_WITH_BOOKS_ON_LOAN =
            "Cannot unregister %1$s!\n"
            + "Please return books currently on loan or delete those books before unregistering.";

    // Loan messages
    public static final String MESSAGE_BOOK_ON_LOAN = "%1$s is already on loan!";
    public static final String MESSAGE_LOAN_ID_DOES_NOT_EXISTS = "LoanId %s does not exists in LoanRecords!";
    public static final String MESSAGE_BOOK_CANNOT_BE_RENEWED_ANYMORE =
            "%1$s has already been renewed the maximum number of times!";
    public static final String MESSAGE_BOOK_IS_OVERDUE = "%1$s is already overdue and cannot be renewed!";
    public static final String MESSAGE_NO_RETURNABLE_BOOKS = "Borrower does not have any books to return!";
    public static final String MESSAGE_NO_RENEWABLE_BOOKS =
            "Borrower does not have any books that can be renewed currently!\n"
            + "Books that have just been loaned or renewed, books that are overdue and books that have met "
            + "the maximum renew count cannot be renewed.";
    public static final String MESSAGE_LOAN_STATE_CONSTRAINTS = "Only 1 of -available / -loaned / -overdue flags can "
            + "be used at any time";
    public static final String MESSAGE_CANNOT_RENEW_IMMEDIATELY =
            "Cannot renew a book immediately after loaning or renewing it!";

    // Fine messages
    public static final String MESSAGE_NO_OUTSTANDING_FINE = "No outstanding fines!";

    // Undo/Redo messages
    public static final String MESSAGE_CANNOT_UNDO_COMMAND = "There are no commands to undo!";
    public static final String MESSAGE_CANNOT_REDO_COMMAND = "There are no commands to redo!";
}
