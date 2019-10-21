package seedu.address.reimbursement.ui;

/**
 * Stores the messages to be printed as a response to the user.
 */
public class ReimbursementMessages {

    public static final String BACK_COMMAND = "List all reimbursements.\n";

    public static final String SORT_BY_NAME = "The reimbursement list has been sorted by person's name.";

    public static final String SORT_BY_AMOUNT = "The reimbursement list has been sorted by amount of money.";

    public static final String SORT_BY_DEADLINE = "The reimbursement list has been sorted by deadline of the "
            + "reimbursement.";

    public static final String NO_SUCH_COMMAND = "Sorry! Please type with these commands:\n"
            + "deadline\ndone\nfind\nback\nsort name\nsort amount\nsort date\n";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Sorry! Please type with these parameters:\n"
            + "p/PERSON\n dt/DATE (eg.02-Sep-2019)";

    public static final String MESSAGE_INVALID_DEADLINECOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "deadline p/PERSON\n dt/DATE (eg.02-Sep-2019)";

    public static final String MESSAGE_INVALID_DONECOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "done p/PERSON";

    public static final String MESSAGE_INVALID_BACKCOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "back";

    public static final String MESSAGE_INVALID_FINDCOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "find p/PERSON";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    public static final String MESSAGE_INVALID_SORT_COMMAND_FORMAT = "Sorry! Please input sort by date, amount "
            + "or name.";

    public static final String MESSAGE_FIND_REIMBURSEMENT = "Find person %s\n%s";

    public static final String MESSAGE_ADD_DEADLINE = "Added deadline %s to\n%s";

    public static final String MESSAGE_DONE_REIMBURSEMENT = "Done:\n%s";

}
