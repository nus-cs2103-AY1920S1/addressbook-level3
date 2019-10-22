package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.Split;

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
    public ObservableList<LedgerOperation> getLoanHistory() {
        return null;
    }

    @Override
    public ObservableList<LedgerOperation> getSortedLoanHistory(Comparator<LedgerOperation> t) {
        return null;
    }

    @Override
    public Amount getBalance() {
        return pot;
    }
}
