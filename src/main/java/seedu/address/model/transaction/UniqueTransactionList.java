package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of transactions that enforces uniqueness between its elements and does not allow nulls.
 * A transaction is considered unique by comparing using {@code Transaction#isSameTransaction(Transaction)}.
 * As such, adding and updating of transactions uses Transaction#isSameTransaction(Transaction) for equality so as
 * to ensure that the transaction being added or updated is unique in terms of identity in the UniqueTransactionList.
 * However, the removal of a Transaction uses Transaction#equals(Object) so
 * as to ensure that the transaction with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Transaction#isSameTransaction(Transaction)
 */
public class UniqueTransactionList implements Iterable<Transaction> {

    private final ObservableList<Transaction> internalList = FXCollections.observableArrayList();
    private final ObservableList<Transaction> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent transaction as the given argument.
     */
    public boolean contains(Transaction toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a transaction to the list.
     * The transaction must not already exist in the list.
     * @param toAdd
     */
    public void add(Transaction toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the list.
     * The transaction identity of {@code editedTransaction} must not be the same as
     * another existing transaction in the list.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedTransaction) && contains(editedTransaction)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedTransaction);
    }

    /**
     * Removes the equivalent transaction from the list.
     * The transaction must exist in the list.
     */
    public void remove(Transaction toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code transactions}.
     * {@code transaction} must not contain duplicate transactions.
     */
    public void setTransactions(List<Transaction> transactions) {
        requireAllNonNull(transactions);
        if (!transactionsAreUnique(transactions)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(transactions);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     * @return
     */
    public ObservableList<Transaction> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Transaction> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTransactionList // instanceof handles nulls
                        && internalList.equals(((UniqueTransactionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code transactions} contains only unique transactions.
     */
    private boolean transactionsAreUnique(List<Transaction> transactions) {
        for (int i = 0; i < transactions.size() - 1; i++) {
            for (int j = i + 1; j < transactions.size(); j++) {
                if (transactions.get(i).equals(transactions.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
