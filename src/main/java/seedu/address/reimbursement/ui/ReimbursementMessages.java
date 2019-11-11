package seedu.address.reimbursement.ui;

/**
 * Stores the messages to be printed as a response to the user.
 */
public class ReimbursementMessages {
    public static final String MESSAGE_BEGINNING_FOR_INVALID_COMMAND = "Sorry! Please input the correct "
            + "command format:\n";

    public static final String BACK_COMMAND = "Here are all the reimbursements.";

    public static final String SORT_BY_NAME = "The reimbursement list has been sorted by person's name.";

    public static final String SORT_BY_AMOUNT = "The reimbursement list has been sorted by amount of money.";

    public static final String SORT_BY_DEADLINE = "The reimbursement list has been sorted by deadline of the "
            + "reimbursement.";

    public static final String NO_SUCH_COMMAND = "Sorry! Please type with these commands:\n"
            + "deadline\ndone\nfind\nback\nsort name\nsort amount\nsort date\nexit";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Sorry! Please type with these parameters:\n"
            + "p/PERSON\n dt/DATE (eg.02-Sep-2019)";

    public static final String MESSAGE_INVALID_DEADLINECOMMAND_FORMAT = MESSAGE_BEGINNING_FOR_INVALID_COMMAND
            + "deadline: Adds deadline to a reimbursement record for a member.\n"
            + "Parameters:\np/PERSON\n dt/DATE (eg.02-Sep-2019)";

    public static final String MESSAGE_INVALID_DONECOMMAND_FORMAT = MESSAGE_BEGINNING_FOR_INVALID_COMMAND
            + "done: Marks a reimbursement record as done and removes it.\n"
            + "Parameters:\np/PERSON";

    public static final String MESSAGE_INVALID_BACKCOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "back";

    public static final String MESSAGE_INVALID_FINDCOMMAND_FORMAT = MESSAGE_BEGINNING_FOR_INVALID_COMMAND
            + "find: Finds the reimbursement record according to the member's name specified (CASE_SENSITIVE)"
            + "Parameters:\np/PERSON";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    public static final String MESSAGE_INVALID_SORT_COMMAND_FORMAT = "Sorry! Please input sort by date, amount "
            + "or name.";

    public static final String MESSAGE_FIND_REIMBURSEMENT = "Find person %s\n%s";

    public static final String MESSAGE_ADD_DEADLINE = "Added deadline %s to\n%s";

    public static final String MESSAGE_DONE_REIMBURSEMENT = "Done:\n%s";

    public static final String MESSAGE_NO_COMMAND = "Please input a valid command. The commands include\n"
            + "deadline, done, find, sort, back, go and exit.";

}
