package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Split;
import seedu.address.model.transaction.Transaction;

import java.util.Comparator;

/**
 * Separate field in BankAccount to store transactions related to split
 */
public class Ledger implements ReadOnlyLedger{
    private Amount pot;
    private UniquePersonList people;

    public Ledger() {
        pot = Amount.zero();
        people = new UniquePersonList();
    }


    /**
     * Adds transaction into a separate splitHistory
     * @param transaction
     */
    public void addSplit(Split transaction) {
        pot = transaction.handleBalance(pot, people);
    }

    @Override
    public ObservableList<Transaction> getLoanHistory() {
        return null;
    }

    @Override
    public ObservableList<Transaction> getSortedLoanHistory(Comparator<Transaction> t) {
        return null;
    }

    @Override
    public Amount getBalance() {
        return pot;
    }
}
