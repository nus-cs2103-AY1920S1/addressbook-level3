package seedu.address.model;

import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private Amount balance;
    private List<Transaction> transactions;

    BankAccount() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction txn) {
        transactions.add(txn);
        Amount newBalance = txn.handleBalance(this.balance);
        this.balance = newBalance;
    }
}
