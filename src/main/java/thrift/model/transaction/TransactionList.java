package thrift.model.transaction;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thrift.commons.core.index.Index;
import thrift.model.transaction.exceptions.TransactionNotFoundException;

/**
 * A list of Transactions that does not allow nulls. However, the removal of a transaction
 * uses Transaction#equals(Object) so as to ensure that the transaction with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class TransactionList implements Iterable<Transaction> {

    private final ObservableList<Transaction> internalList = FXCollections.observableArrayList();
    private final ObservableList<Transaction> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the supplied Transaction object is inside this transaction list.
     */
    public boolean contains(Transaction toCheck) {
        requireAllNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTransaction);
    }

    /**
     * Adds a transaction to the list.
     */
    public void add(Transaction toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Adds a transaction to a specified index in the list.
     */
    public void add(Transaction toAdd, Index index) {
        requireNonNull(toAdd);
        internalList.add(index.getZeroBased(), toAdd);
    }

    /**
     * Replaces the transaction {@code target} in the list with {@code updatedTransaction}.
     * {@code target} must exist in the list.
     */
    public void setTransaction(Transaction target, Transaction updatedTransaction) {
        requireAllNonNull(target, updatedTransaction);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TransactionNotFoundException();
        }

        internalList.set(index, updatedTransaction);
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

    /**
     * Remove the last transaction from the list.
     */
    public void removeLast() {
        internalList.remove(internalList.size() - 1);
    }

    public void setTransactions(TransactionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code transactions}.
     */
    public void setTransactions(List<Transaction> transactions) {
        requireAllNonNull(transactions);
        internalList.setAll(transactions);
    }

    /**
     * Retrieve the last transaction.
     *
     * @return the last transaction
     */
    public Transaction getLast() {
        return internalList.get(internalList.size() - 1);
    }


    /**
     * Returns an Optional that contains the {@link Index} of the {@code transaction}.
     *
     * @param transaction is the transaction that you are interested in its index in the full transaction list.
     * @return an Optional containing the index of the transaction.
     */
    public Optional<Index> getIndex(Transaction transaction) {
        requireNonNull(transaction);
        for (int i = 0; i < internalList.size(); i++) {
            if (transaction == internalList.get(i)) {
                return Optional.of(Index.fromZeroBased(i));
            }
        }
        return Optional.empty();
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
