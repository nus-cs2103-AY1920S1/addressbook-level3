package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    //Command messages
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_LOAN_STATE_CONSTRAINTS = "Only 1 of -available / -loaned / -overdue flags can "
            + "be used at any time";
    public static final String MESSAGE_INVALID_DISPLAY_LIMIT = "Display limit must be a positive integer!";
    public static final String MESSAGE_BOOK_TITLE_TOO_LONG = "Title of book should not be more than 30 characters!";
    public static final String MESSAGE_AUTHOR_NAME_TOO_LONG = "Name of author should not be more than 30 characters!";

    //Book messages
    public static final String MESSAGE_DUPLICATE_BOOK = "Serial number provided is already in use!";
    public static final String MESSAGE_INVALID_BOOK_DISPLAYED_INDEX = "The book index provided is invalid";
    public static final String MESSAGE_BOOKS_LISTED_OVERVIEW = "%1$d books listed!";
    public static final String MESSAGE_NO_SUCH_BOOK = "No such book!";

    //Serial Number messages
    public static final String MESSAGE_INVALID_SERIAL_NUMBER = "Invalid Serial Number! \n%1$s";

    //Borrower messages
    public static final String MESSAGE_DUPLICATE_BORROWER = "Phone/Email is already in used! ";
    public static final String MESSAGE_NO_SUCH_BORROWER_ID = "No such borrower ID!";
    public static final String MESSAGE_NOT_IN_SERVE_MODE = "Not in Serve mode! Enter Serve mode to use this command!";

    //Loan messages
    public static final String MESSAGE_BOOK_ON_LOAN = "%1$s is already on loan!";
    public static final String MESSAGE_BOOK_NOT_ON_LOAN = "%1$s is not on loan!";
    public static final String MESSAGE_LOAN_ID_DOES_NOT_EXISTS = "LoanId %s does not exists in LoanRecords!";
    public static final String MESSAGE_NOT_LOANED_BY_BORROWER = "%1$s\ndoes not loan\n%2$s!";
    public static final String MESSAGE_BOOK_CANNOT_BE_RENEWED_ANYMORE =
            "%1$s has already been renewed the maximum number of times!";
    public static final String MESSAGE_BOOK_IS_OVERDUE = "%1$s is already overdue and cannot be renewed!";

    // Fine messages
    public static final String MESSAGE_NO_OUTSTANDING_FINE = "No outstanding fines!";
}
