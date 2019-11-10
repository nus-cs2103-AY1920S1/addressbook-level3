package seedu.ichifund.model.loan;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.loan.exceptions.LoanNotFoundException;

/**
 * A list of Loans that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 */
public class LoanList implements Iterable<Loan> {
    private final ObservableList<Loan> internalList = FXCollections.observableArrayList();
    private final ObservableList<Loan> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a Loan to the list and sorts the list.
     */
    public void add(Loan toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        internalList.sort(Comparator.naturalOrder());
    }

    /**
     * Replaces the loan {@code target} in the list with {@code editedLoan}.
     * {@code target} must exist in the list.
     */
    public void setLoan(Loan target, Loan editedLoan) {
        requireAllNonNull(target, editedLoan);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LoanNotFoundException();
        }

        internalList.set(index, editedLoan);
        internalList.sort(Comparator.naturalOrder());
    }

    /**
     *  Removes a Loan object from current List.
     * @param toRemove
     */
    public void remove(Loan toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LoanNotFoundException();
        }
    }

    /**
     * Checks if passses @code loanlist contains a loan
     * @param loan
     * @return
     */
    public boolean contains(Loan loan) {
        requireNonNull(loan);
        if (internalList.contains(loan)) {
            return true;
        } else {
            return false;
        }
    }

    public void setLoans(LoanList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        internalList.sort(Comparator.naturalOrder());
    }

    /**
     * Replaces the contents of this list with {@code loans}.
     */
    public void setLoans(List<Loan> loans) {
        requireAllNonNull(loans);
        internalList.setAll(loans);
        internalList.sort(Comparator.naturalOrder());
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

    /** Returns the latest loan earlier than the current time */
    public Optional<Loan> getLatestLoan() {
        internalList.sort(Comparator.naturalOrder());

        // Searches for first loan that is earlier than current time
        for (Loan loan : internalList) {
            if (loan.getStartDate().compareTo(Date.getCurrent()) >= 0) {
                return Optional.of(loan);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoanList // instanceof handles nulls
                && internalList.equals(((LoanList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
