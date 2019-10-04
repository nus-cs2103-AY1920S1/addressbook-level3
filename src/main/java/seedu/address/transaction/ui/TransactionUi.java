package seedu.address.transaction.ui;

import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

public class TransactionUi {
    public static final String NO_SUCH_COMMAND =  "Sorry! There is no such command.";

    public static final String MESSAGE_INVALID_ADDCOMMAND_FORMAT = "Sorry! Please type add with parameters:\n" +
            "dt/DATE d/DESCRIPTION c/CATEGORY a/AMOUNT p/PERSON";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Sorry! Please type edit with the index " +
            "and any parameters to be edited:\n" +
            "dt/DATE d/DESCRIPTION c/CATEGORY a/AMOUNT p/PERSON";

    public static final String MESSAGE_NOT_EDITED = "Sorry! Did not manage to edit transaction!";

    public static final String NO_SUCH_INDEX_TRANSACTION = "There is no transaction at inputted index.";

    public static final String NOT_A_NUMBER = "Please input the index of the transaction.";

    public static String editedTransaction(Transaction editedTransaction) {
        return "Edited Transaction:\n " + editedTransaction;
    }

    public String addedTransaction(Transaction transaction) {
        return "Added Transaction:\n " + transaction;
    }

    public String deletedTransaction(Transaction transaction) {
        return "Deleted Transaction:\n " + transaction;
    }

    public String printAllTransactions(TransactionList transactionList) {
        String msg = "";
        try {
            for (int i = 0; i < transactionList.size(); i++) {
                msg = msg + transactionList.get(i) + "\n";
            }
        } catch (Exception e) {
            System.out.println("problem here.");
        }
        return msg;
    }
}
