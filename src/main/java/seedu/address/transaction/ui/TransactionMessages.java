package seedu.address.transaction.ui;

/**
 * Formats the Ui return responses for different commands to be shown to user.
 */
public class TransactionMessages {
    public static final String MESSAGE_BEGINNING_FOR_INVALID_COMMAND = "Sorry! Please Please input the correct "
            + "command format:\n";

    public static final String MESSAGE_NO_COMMAND = "Please input a valid command. The commands include "
            + "add, delete, edit, sort, find, back, go and exit.";

    public static final String MESSAGE_NO_SUCH_SORT_COMMAND = "The transactions can be sorted by date, "
            + "amount and name.\nPlease input sort by date/amount/name";

    public static final String MESSAGE_RESET_TO_ORIGINAL_ORDER = "The transactions are now reset to original "
            + "order of input when treasurerPro was just open.";

    public static final String MESSAGE_INVALID_FIND_COMMAND_FORMAT = MESSAGE_BEGINNING_FOR_INVALID_COMMAND
            + "find: Finds all transactions which contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers\n"
            + "Parameters:\nKEYWORDS [MORE_KEYWORDS]...\nExample:\nfind poster printing";

    public static final String MESSAGE_SORTED_BY_NAME = "The transactions are now sorted by name.";

    public static final String MESSAGE_SORTED_BY_DATE = "The transactions are now sorted by date.";

    public static final String MESSAGE_SORTED_BY_AMOUNT = "The transactions are now sorted by the amount "
            + "of money involved.";

    public static final String MESSAGE_NO_SUCH_PERSON = "Sorry! There is no such member in our database. "
            + "Do add the member in.";

    public static final String MESSAGE_INVALID_ADD_COMMAND_FORMAT = MESSAGE_BEGINNING_FOR_INVALID_COMMAND
            + "add: Add transaction into the table.\n"
            + "Parameters:\n"
            + "dt/DATE (eg.02-Sep-2019) d/DESCRIPTION c/CATEGORY a/AMOUNT p/PERSON";

    public static final String MESSAGE_INVALID_EDIT_COMMAND_FORMAT = MESSAGE_BEGINNING_FOR_INVALID_COMMAND
            + "edit: Edits a transaction in the table.\n"
            + "Parameters:\nID\n[dt/DATE] (eg.02-Sep-2019)\n [d/DESCRIPTION]\n[c/CATEGORY]\n[a/AMOUNT]\n[p/PERSON]";

    public static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT = MESSAGE_BEGINNING_FOR_INVALID_COMMAND
            + "delete: Delete transaction(s).\n"
            + "Parameters:\nID\nOR "
            + "\np/NAME\nExample:\ndelete 2, delete p/Alex Yeoh";

    public static final String MESSAGE_PERSON_CANNOT_BE_DELETED = "Sorry! This person that you are intending to "
            + "delete still has transaction records.\nPlease make sure the person is reimbursed and delete "
            + "those transaction records first.";

    public static final String MESSAGE_WRONG_DATE_FORMAT = "Sorry! The date should be in the format DD-MMM-YYYY "
            + "and is case-sensitive:\n"
            + "02-Sep-2019 or 24-Aug-2019 etc";

    public static final String MESSAGE_NO_SUCH_TRANSACTION = "Sorry! There is no such transaction of the inputted "
            + "index.\nPlease make sure the index is within the table.";

    public static final String MESSAGE_NO_SUCH_TRANSACTION_OF_PERSON = "Sorry! There is no such transaction of the "
            + "inputted person.";

    public static final String MESSAGE_POSITIVE_TRANSACTION_EDITED = "Edited %s";

    public static final String MESSAGE_NEGATIVE_TRANSACTION_EDITED = "Edited %s";

    public static final String MESSAGE_FIND_COMMAND = "I've found %s matching transactions!";

    public static final String MESSAGE_ADD_POSITIVE_TRANSACTION = "Added %s";

    public static final String MESSAGE_ADD_NEGATIVE_TRANSACTION = "Added %s";

    public static final String MESSAGE_DELETE_POSITIVE_TRANSACTION = "Deleted %s";

    public static final String MESSAGE_DELETE_NEGATIVE_TRANSACTION = "Deleted %s";

    public static final String MESSAGE_DELETE_BY_PERSON = "I've deleted all transactions by this member:\n%s";

    public static final String MESSAGE_WRONG_AMOUNT_FORMAT = "Please input a number for the amount.";

    public static final String MESSAGE_AMOUNT_TOO_LARGE = "Sorry! The positive amount entered "
            + "is too large. Try to separate it into a few transactions instead.";

    public static final String MESSAGE_AMOUNT_TOO_SMALL = "Sorry! The negative amount entered "
            + "is too small. Try to separate it into a few transactions instead.";

    public static final String MESSAGE_BACKED = "Here are all the transactions.";

    public static final String MESSAGE_NO_ZERO_ALLOWED = "Sorry! The amount should not be zero.";

    public static final String MESSAGE_INVALID_AMOUNT = "Invalid amount entered. It should be between -999999.99 "
            + "to 999999.99 and not be 0.";
}
