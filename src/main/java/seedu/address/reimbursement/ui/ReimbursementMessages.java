package seedu.address.reimbursement.ui;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.reimbursement.model.Reimbursement;

/**
 * Stores the messages to be printed as a response to the user.
 */
public class ReimbursementMessages {
    public static final String LIST_COMMAND = "List all reimbursements.\n";
    public static final String SORT_BY_NAME = "The reimbursement list has been sorted by person's name.";
    public static final String SORT_BY_AMOUNT = "The reimbursement list has been sorted by amount of money.";
    public static final String SORT_BY_DEADLINE = "The reimbursement list has been sorted by deadline of the "
            + "reimbursement.";
    public static final String NO_SUCH_COMMAND = "Sorry! Please type with these commands:\n"
            + "deadline done find sortname sortamount sortdeadline";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Sorry! Please type with these parameters:\n"
            + "p/PERSON\n dt/DATE (yyyymmdd. eg.20191007, 20200501 etc)";
    public static final String MESSAGE_INVALID_DEADLINECOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "deadline p/PERSON\n dt/DATE (yyyymmdd. eg.20191007, 20200501 etc)";
    public static final String MESSAGE_INVALID_DONECOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "done p/PERSON";
    public static final String MESSAGE_INVALID_LISTCOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "list";
    public static final String MESSAGE_INVALID_FINDCOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "find p/PERSON";
    public static final String MESSAGE_INVALID_SORTNAMECOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "sortname";
    public static final String MESSAGE_INVALID_SORTAMOUNTCOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "sortamount";
    public static final String MESSAGE_INVALID_SORTDEADLINECOMMAND_FORMAT = "Sorry! Please type with parameters:\n"
            + "sortdeadline";

    private static final Logger logger = LogsCenter.getLogger(ReimbursementMessages.class);

    /**
     * @param found the reimbursement object to be displayed to the user.
     * @return a message describing the reimbursement to the user.
     */
    public static String findReimbursement(Reimbursement found) {
        return "Find person " + found.getPerson().getName().toString() + System.lineSeparator() + found.toString();
    }

    /**
     * @param rmb the reimbursement object to be displayed to the user.
     * @return a message describing the new deadline and reimbursement to the user.
     */
    public static String addDeadline(Reimbursement rmb) {
        String msg = "Added " + rmb.getDeadline().toString() + " to" + System.lineSeparator();
        msg = msg + rmb.toStringNoDeadline();
        return msg;
    }

    /**
     * @param rmb the reimbursement object to be displayed to the user.
     * @return a message describing the reimbursement to the user.
     */
    public static String doneReimbursement(Reimbursement rmb) {
        String msg = "Done" + System.lineSeparator();
        msg = msg + rmb.toString();
        return msg;
    }

}
