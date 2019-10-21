package seedu.address.model;

import java.util.Comparator;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Transaction;

/**
 * Unmodifiable view of an bank account
 */
public interface ReadOnlyBankAccount {

    /**
     * Returns an unmodifiable view of the transaction list.
     * This list will not contain any duplicate transactions.
     */
    ObservableList<Transaction> getTransactionHistory();

    /**
     * Returns an unmodifiable view of the sorted transaction list.
     * This list will not contain any duplicate transactions
     */
    ObservableList<Transaction> getSortedTransactionHistory(Comparator<Transaction> t);

    Amount getBalance();
}
