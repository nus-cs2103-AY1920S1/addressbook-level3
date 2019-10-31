package seedu.address.model;

import java.util.Comparator;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.Split;
import seedu.address.model.transaction.UniqueLedgerOperationList;

import static java.util.Objects.requireNonNull;

/**
 * Separate field in BankAccount to store transactions related to split
 */
public class Ledger implements ReadOnlyLedger {
    private Amount pot;
    private UniquePersonList people;
    private UniqueLedgerOperationList ledgerHistory;

    public Ledger() {
        pot = Amount.zero();
        people = new UniquePersonList();
    }

    public Ledger(ReadOnlyLedger ledger) {
        requireNonNull(ledger);
        pot = ledger.getBalance();
        setPersonList(ledger);
        setLedgerHistory(ledger);
    }

    private void setLedgerHistory(ReadOnlyLedger ledger) {
        ledgerHistory.setTransactions(ledger.getLedgerHistory());
    }

    private void setPersonList(ReadOnlyLedger ledger) {
        people.setPersons(ledger.getPeople());
    }


    /**
     * Adds transaction into a separate splitHistory
     * @param transaction
     */
    public void addOperation(LedgerOperation transaction) {
        pot = transaction.handleBalance(pot, people);
        ledgerHistory.add(transaction);
    }

    @Override
    public ObservableList<LedgerOperation> getLedgerHistory() {
        return ledgerHistory.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<LedgerOperation> getSortedLedgerHistory(Comparator<LedgerOperation> t) {
        return getLedgerHistory().sorted(t);
    }

    @Override
    public ObservableList<Person> getPeople() {
        return people.asUnmodifiableObservableList();
    }

    @Override
    public Amount getBalance() {
        return pot;
    }

    public void resetData(ReadOnlyLedger otherLedger) {
        this.pot = otherLedger.getBalance();
    }
}
