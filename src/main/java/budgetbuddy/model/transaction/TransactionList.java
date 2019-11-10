package budgetbuddy.model.transaction;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.transaction.exceptions.TransactionNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of Transactions that does not allow nulls.
 */
public class TransactionList implements Iterable<Transaction> {

    public static final String MESSAGE_CONSTRAINTS = "TransactionList can not be null";
    private final ObservableList<Transaction> internalList = FXCollections.observableArrayList();
    private final ObservableList<Transaction> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public TransactionList() {
    }

    /**
     * Returns true if the list contains an equivalent Transaction as the given argument.
     */
    public boolean contains(Transaction toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a Transaction to the list. Multiple identical transactions are allowed.
     */
    public void add(Transaction toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }


    /**
     * Returns the Transaction at the specified index in the list.
     * @param toGet The index of the target transaction.
     * @throws TransactionNotFoundException If the transaction is not in the list.
     */
    public Transaction getTransaction(Index toGet) throws TransactionNotFoundException {
        if (toGet.getOneBased() > internalList.size()) {
            throw new TransactionNotFoundException();
        }
        return internalList.get(toGet.getZeroBased());
    }

    /**
     * Replaces the Transaction at the index {@code txnIndex} in the list with {@code editedTransaction}.
     * {@code txnIndex} must be a valid index of a Transaction in the list.
     */
    public void setTransaction(Index txnIndex, Transaction editedTransaction) {
        requireAllNonNull(txnIndex, editedTransaction);

        internalList.set(txnIndex.getZeroBased(), editedTransaction);
    }

    /**
     * Replaces all transactions within the list with the transactions from another TransactionList.
     * {@code toCopy} must not be a null list.
     */
    public void setAll(TransactionList toCopy) {
        requireNonNull(toCopy);
        if (toCopy.getTransactionsCount() > 0) {
            //there are transactions to be copied over
            internalList.setAll(toCopy.internalList);
        }
    }

    /**
     * Removes the equivalent Transaction from the list.
     * The Transaction must exist in the list.
     */
    public void remove(Transaction toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TransactionNotFoundException();
        }
    }

    /**
     * Returns the current number of transactions in the list.
     */
    public int getTransactionsCount() {
        return internalList.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Transaction> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Sorts the backing list by providing a comparator
     */
    public void sort(Comparator<Transaction> c) {
        internalList.sort(c);
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
