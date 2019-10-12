package seedu.address.model.person.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of loans that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class LoanList implements Iterable<Loan> {

    private final ObservableList<Loan> internalList = FXCollections.observableArrayList();
    private final ObservableList<Loan> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Checks if the list contains a loan equivalent to the given loan.
     * @param toCheck The given loan to check the list for.
     * @return True if the list contains the given loan, false otherwise.
     */
    public boolean contains(Loan toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a loan to the list.
     * @param toAdd
     */
    public void add(Loan toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the loan {@code target} in the list with {@code editedLoan}.
     * {@code target} must exist in the list.
     * @param target The target loan to be replaced.
     * @param editedLoan The edited loan to replace the target loan with.
     */
    public void setLoan(Loan target, Loan editedLoan) {
        requireAllNonNull(target, editedLoan);

        int index = internalList.indexOf(target);
        if (index == -1) {
            // TODO Handle loan not found in list.
        }

        internalList.set(index, editedLoan);
    }

    /**
     * Removes the equivalent loan from the list.
     * The loan must exist in the list.
     * @param toRemove The loan to be removed from the list.
     */
    public void remove(Loan toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            // TODO Handle loan not found in list.
            System.out.println("Loan not found.");
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
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
