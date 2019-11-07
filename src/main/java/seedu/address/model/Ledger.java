package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.UniqueLedgerOperationList;

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
        ledgerHistory = new UniqueLedgerOperationList();
    }

    public Ledger(ReadOnlyLedger ledger) {
        this();
        requireNonNull(ledger);
        resetData(ledger);
    }

    private void setLedgerHistory(ReadOnlyLedger ledger) {
        requireNonNull(ledger.getLedgerHistory());
        this.ledgerHistory.setLedgerOperations(ledger.getLedgerHistory());
        recalculatePot();
    }

    private void setPersonList(ReadOnlyLedger ledger) {
        requireNonNull(ledger.getPeople());
        people.setPersons(ledger.getPeople());
    }


    /**
     * Adds transaction into a separate splitHistory
     *
     * @param transaction
     */
    public void addOperation(LedgerOperation transaction) {
        pot = transaction.handleBalance(pot, people);
        ledgerHistory.add(transaction);
        removePeopleWithNoBalance();
    }

    /**
     * Removes person from {@code people} that is not in deficit or surplus.
     *
     */
    private void removePeopleWithNoBalance() {
        List<Person> filtered = people.asUnmodifiableObservableList().stream()
                .filter(person -> !person.getBalance().equals(Amount.zero()))
                .collect(Collectors.toList());
        people.setPersons(filtered);
    }

    /**
     * Removes {@code key} from this {@code BankAccount}.
     * {@code key} must exist in the bank account.
     */
    public void remove(LedgerOperation key) {
        ledgerHistory.remove(key);
        recalculatePot();
    }

    /**
     * Updates the people and pot in ledger after removing a certain LedgerOperation
     */
    private void recalculatePot() {
        Amount updatedAmount = Amount.zero();
        UniquePersonList updatedPeople = new UniquePersonList();
        for (LedgerOperation lo : ledgerHistory) {
            updatedAmount = lo.handleBalance(updatedAmount, updatedPeople);
        }
        pot = updatedAmount;
        people.setPersons(updatedPeople);
        removePeopleWithNoBalance();
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

    /**
     * Resets the existing data of this {@code Ledger} with {@code otherLedger}.
     */
    public void resetData(ReadOnlyLedger otherLedger) {
        requireNonNull(otherLedger);
        pot = otherLedger.getBalance();
        setLedgerHistory(otherLedger);
        setPersonList(otherLedger);
    }

    public boolean has(LedgerOperation ledgerOperation) {
        return ledgerHistory.contains(ledgerOperation);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Ledger)) {
            return false;
        }

        Ledger otherLedger = (Ledger) other;
        return this.pot.equals(otherLedger.pot)
            && this.people.equals(otherLedger.people)
            && this.ledgerHistory.equals(otherLedger.ledgerHistory);
    }

    public void set(LedgerOperation target, LedgerOperation source) {
        ledgerHistory.set(target, source);
        recalculatePot();
    }
}
