package seedu.ichifund.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.transaction.exceptions.TransactionNotFoundException;

/**
 * A list of transactions that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 */
public class TransactionList implements Iterable<Transaction> {

    private final ObservableList<Transaction> internalList = FXCollections.observableArrayList();
    private final ObservableList<Transaction> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a transaction to the list and sorts the list.
     */
    public void add(Transaction toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        internalList.sort(Comparator.naturalOrder());
    }

    /**
     * Replaces the transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the list.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TransactionNotFoundException();
        }

        internalList.set(index, editedTransaction);
        internalList.sort(Comparator.naturalOrder());
    }

    /**
     * Removes the equivalent transaction from the list.
     * The transaction must exist in the list.
     */
    public void remove(Transaction toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TransactionNotFoundException();
        }
    }

    public void setTransactions(TransactionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        internalList.sort(Comparator.naturalOrder());
    }

    /**
     * Replaces the contents of this list with {@code transactions}.
     */
    public void setTransactions(List<Transaction> transactions) {
        requireAllNonNull(transactions);
        internalList.setAll(transactions);
        internalList.sort(Comparator.naturalOrder());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Transaction> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Transaction> iterator() {
        return internalList.iterator();
    }

    /** Returns the latest transaction earlier than the current time */
    public Optional<Transaction> getLatestTransaction() {
        internalList.sort(Comparator.naturalOrder());

        // Searches for first transaction that is earlier than current time
        for (Transaction transaction : internalList) {
            if (transaction.getDate().compareTo(Date.getCurrent()) >= 0) {
                return Optional.of(transaction);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionList // instanceof handles nulls
                && internalList.equals(((TransactionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

