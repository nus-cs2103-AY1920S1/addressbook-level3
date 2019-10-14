package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.UniqueTransactionList;

import static java.util.Objects.requireNonNull;

/**
 * Bank account of the user.
 */
public class BankAccount implements ReadOnlyBankAccount{
    private Amount balance;
    private UniqueTransactionList transactions;

    public BankAccount() {
        transactions = new UniqueTransactionList();
    }

    BankAccount(ReadOnlyBankAccount bankAccount) {
        this();
        resetData(bankAccount);
    }

    private void resetData(ReadOnlyBankAccount bankAccount) {
        requireNonNull(bankAccount);

        setTransactions(bankAccount.getTransactionHistory());
    }

    private void setTransactions(List<Transaction> transactionHistory) {
        this.transactions.setTransactions(transactionHistory);
    }

    /**
     * Adds a transaction to the bank account.
     * @param txn Transaction to be added to bank account.
     */
    public void addTransaction(Transaction txn) {
        transactions.add(txn);
        Amount newBalance = txn.handleBalance(this.balance);
        this.balance = newBalance;
    }

    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return transactions.contains(transaction);
    }

    @Override
    public ObservableList<Transaction> getTransactionHistory() {
        return transactions.asUnmodifiableObservableList();
    }
}
