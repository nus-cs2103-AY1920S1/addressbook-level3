package seedu.address.transaction.ui;

import java.util.logging.Logger;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

public class TransactionMessages {
    private static final Logger logger = LogsCenter.getLogger(TransactionMessages.class);

    public static final String NO_SUCH_PERSON = "Sorry! There is no such member in our database. Do add the member in.";

    public static final String NO_SUCH_COMMAND =  "Sorry! There is no such command.";

    public static final String MESSAGE_INVALID_ADDCOMMAND_FORMAT = "Sorry! Please type add with parameters:\n" +
            " dt/DATE (eg.21-Sep-2019, 24-Aug-2019 etc)\n d/DESCRIPTION\n c/CATEGORY\n a/AMOUNT\n p/PERSON";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Sorry! Please type edit with the index " +
            "and any parameters to be edited:\n" +
            " dt/DATE\n d/DESCRIPTION\n c/CATEGORY\n a/AMOUNT\n p/PERSON";

    public static final String MESSAGE_NOT_EDITED = "Sorry! Did not manage to edit transaction!";

    public static final String NO_SUCH_INDEX_TRANSACTION = "There is no transaction at the inputted index.";

    public static final String NOT_A_NUMBER = "Please input the index of the transaction.";

    public static String editedTransaction(Transaction editedTransaction) {
        return "Edited Transaction:\n" + editedTransaction;
    }

    public String addedTransaction(Transaction transaction) {
        return "Added Transaction:\n" + transaction;
    }

    public String deletedTransaction(Transaction transaction) {
        return "Deleted Transaction:\n" + transaction;
    }

    public String printAllTransactionsAndPerson(TransactionList transactionList) {
        String msg = "";
        try {
            for (int i = 0; i < transactionList.size(); i++) {
                msg = msg + transactionList.get(i) + "\n" + transactionList.get(i).getPerson() + "\n";
            }
        } catch (Exception e) {
            logger.severe("problem reading transaction list.");
        }
        return msg;
    }
}
