package seedu.address.model;

import seedu.address.model.person.UniquePersonList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.SplitTransaction;
import seedu.address.model.transaction.UniqueTransactionList;

/**
 * Separate field in BankAccount to store transactions related to split
 */
public class Ledger {
    private Amount pot;
    private UniqueTransactionList splitHistory;
    private UniquePersonList people;

    public Ledger() {
        pot = new Amount(0);
        splitHistory = new UniqueTransactionList();
        people = new UniquePersonList();
    }


    /**
     * Adds transaction into a separate splitHistory
     * @param transaction
     */
    public void addSplitTransaction(SplitTransaction transaction) {
        pot = transaction.handleBalance(pot, people);
        splitHistory.add(transaction);
    }
}
