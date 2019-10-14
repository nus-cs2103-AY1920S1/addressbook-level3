package seedu.address.transaction.ui;

import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Transaction;

/**
 * Formats the Ui return responses for different commands to be shown to user.
 */
public class TransactionMessages {
    public static final String MESSAGE_DUPLICATE_TRANSACTION = "This transaction is already recorded.";

    public static final String MESSAGE_NO_SUCH_SORT_COMMAND = "The transactions can be sorted by date, "
            + "amount and name.\nPlease input sort by date/amount/name";

    public static final String MESSAGE_RESET_TO_ORIGINAL_ORDER = "The transactions are now reset to original "
            + "order of input when treasurerPro was just open.";

    public static final String MESSAGE_INVALID_FIND_COMMAND_FORMAT = "Sorry! Please type find with your keywords:\n "
            + "find KEYWORDS (eg. find poster printing)";

    public static final String MESSAGE_SORTED_BY_NAME = "The transactions are now sorted by name.";

    public static final String MESSAGE_SORTED_BY_DATE = "The transactions are now sorted by date.";

    public static final String MESSAGE_SORTED_BY_AMOUNT = "The transactions are now sorted by the amount "
            + "of money involved.";

    public static final String MESSAGE_NO_SUCH_PERSON = "Sorry! There is no such member in our database. "
            + "Do add the member in.";

    public static final String MESSAGE_NO_SUCH_COMMAND = "Sorry! There is no such command.";

    public static final String MESSAGE_INVALID_ADD_COMMAND_FORMAT = "Sorry! Please type add with parameters:\n"
            + "add \ndt/DATE (eg.02-Sep-2019)\n d/DESCRIPTION\n c/CATEGORY\n a/AMOUNT\n p/PERSON";

    public static final String MESSAGE_INVALID_EDIT_COMMAND_FORMAT = "Sorry! Please type edit with the index "
            + "and any parameters to be edited:\n"
            + "add \ndt/DATE (eg.02-Sep-2019)\n d/DESCRIPTION\n c/CATEGORY\n a/AMOUNT\n p/PERSON";

    public static final String MESSAGE_NOT_EDITED = "Sorry! Did not manage to edit transaction!";

    public static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT = "Sorry! Please input delete with the index of "
            + "the transaction or name of person to delete transactions:\n delete ID (eg. delete 2)\nOR "
            + "\ndelete p/NAME";

    public static final String MESSAGE_PERSON_CANNOT_BE_DELETED = "Sorry! This person that you are intending to "
            + "delete still has transaction records.\nPlease make sure the person is reimbursed and delete "
            + "those transaction records first.";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    public static final String MESSAGE_WRONG_DATE_FORMAT = "Sorry! The date should be in the format DD-MMM-YYYY "
            + "and is case-sensitive:\n"
            + "02-Sep-2019 or 24-Aug-2019 etc";

    public static final String MESSAGE_NO_SUCH_TRANSACTION = "Sorry! There is no such transaction of the inputted "
            + "index.\nPlease make sure the index is in the table.";

    public static String editedTransaction(Transaction editedTransaction) {
        return "Edited Transaction:\n" + editedTransaction;
    }

    public static String findCommandMessage(int size) {
        return "I've found " + size + " matching transactions!";
    }

    public static String addedTransaction(Transaction transaction) {
        return "Added Transaction:\n" + transaction;
    }

    public static String deletedTransaction(Transaction transaction) {
        return "Deleted Transaction:\n" + transaction;
    }

    public static String deletedTransactionsOfPerson(Person person) {
        return "I've deleted all transactions by this member:\n" + person.toString();
    }
}
