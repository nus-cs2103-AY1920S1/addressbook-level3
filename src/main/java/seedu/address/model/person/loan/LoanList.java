package seedu.address.model.person.loan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class LoanList implements Iterable<Loan> {

    private final ObservableList<Loan> internalList = FXCollections.observableArrayList();
    private final ObservableList<Loan> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(Loan toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    public void add(Loan toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setLoan(Loan target, Loan editedLoan) {
        requireAllNonNull(target, editedLoan);

        int index = internalList.indexOf(target);
        if (index == -1) {
            // TODO Handle loan not found in list.
        }

        internalList.set(index, editedLoan);
    }

    public void remove(Loan toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            // TODO Handle loan not found in list.
            System.out.println("Loan not found.");
        }
    }

    public ObservableList<Loan> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Loan> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoanList)) {
            return false;
        }

        LoanList otherLoanList = (LoanList) other;
        return internalList.equals(otherLoanList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
