package seedu.address.model;

import seedu.address.model.person.Balance;
import seedu.address.model.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private Balance balance;
    private List<Transaction> transactions;

    BankAccount() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction txn) {
        this.transactions.add(txn);
        balance.updateBalance(txn);
    }
}
