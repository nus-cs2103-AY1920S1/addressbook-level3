package seedu.address.model;

import java.util.Comparator;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;

/**
 * Unmodifiable view of an bank account
 */
public interface ReadOnlyBankAccount {

    /**
     * Returns an unmodifiable view of the transaction list.
     * This list will not contain any duplicate transactions.
     */
    ObservableList<BankAccountOperation> getTransactionHistory();

    /**
     * Returns an unmodifiable view of the sorted transaction list.
     * This list will not contain any duplicate transactions
     */
    ObservableList<BankAccountOperation> getSortedTransactionHistory(Comparator<BankAccountOperation> t);

    Amount getBalance();

    ObservableList<Budget> getBudgetHistory();

    boolean hasTransaction(BankAccountOperation transaction);

    boolean hasBudget(Budget budget);
}
