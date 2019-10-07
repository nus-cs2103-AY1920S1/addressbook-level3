package seedu.address.model.person;

import seedu.address.model.transaction.Transaction;

public class Balance {
    private int balance;

    public Balance(int balance) {
        this.balance = balance;
    }

    public Balance updateBalance(Transaction txn) {
        int newBalance = txn.handleBalance(balance);
        return  new Balance(newBalance);
    }
}
