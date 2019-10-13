package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Transaction;

/**
 * Bank account of the user.
 */
public class BankAccount {
    private Amount balance;
    private List<Transaction> transactions;

    BankAccount() {
        transactions = new ArrayList<>();
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
}
