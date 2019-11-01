package seedu.address.model.transaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.transaction.exceptions.DuplicateTransactionException;
import seedu.address.model.transaction.exceptions.TransactionNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of transactions that enforces uniqueness between its elements and does not allow nulls.
 * A transaction is considered unique by comparing using {@code Transaction#isSameTransaction(Transaction)}.
 * As such, adding and updating of transactions uses Transaction#isSameTransaction(Transaction) for equality so as
 * to ensure that the transaction being added or updated is unique in terms of identity in the UniqueTransactionList.
 * However, the removal of a Transaction uses Transaction#equals(Object) so
 * as to ensure that the transaction with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Transaction#isSameTransaction(BankAccountOperation)
 */
public class UniqueLedgerOperationList implements Iterable<LedgerOperation> {

    private final ObservableList<LedgerOperation> internalList = FXCollections.observableArrayList();
    private final ObservableList<LedgerOperation> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent transaction as the given argument.
     */
    public boolean contains(LedgerOperation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a transaction to the list.
     * The transaction must not already exist in the list.
     *
     * @param toAdd
     */
    public void add(LedgerOperation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTransactionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the list.
     * The transaction identity of {@code editedTransaction} must not be the same as
     * another existing transaction in the list.
     */
    public void setTransaction(LedgerOperation transactionTarget, LedgerOperation transactionEdit) {
        requireAllNonNull(transactionTarget, transactionEdit);

        int index = internalList.indexOf(transactionTarget);
        if (index == -1) {
            throw new TransactionNotFoundException();
        }

        if (!transactionTarget.equals(transactionEdit) && contains(transactionEdit)) {
            throw new DuplicateTransactionException();
        }

        internalList.set(index, transactionEdit);
    }

    /**
     * Removes the equivalent transaction from the list.
     * The transaction must exist in the list.
     */
    public void remove(LedgerOperation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TransactionNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code transactions}.
     * {@code transaction} must not contain duplicate transactions.
     */
    public void setTransactions(List<LedgerOperation> transactions) {
        System.out.println("Hello" + transactions == null);
        requireAllNonNull(transactions);
        if (!transactionsAreUnique(transactions)) {
            throw new DuplicateTransactionException();
        }

        internalList.setAll(transactions);
    }

    public void setTransactions(UniqueLedgerOperationList transactions) {
        setTransactions(transactions.internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return
     */
    public ObservableList<LedgerOperation> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<LedgerOperation> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueLedgerOperationList // instanceof handles nulls
            && internalList.equals(((UniqueLedgerOperationList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code transactions} contains only unique transactions.
     *
     * @param transactions
     */
    private boolean transactionsAreUnique(List<LedgerOperation> transactions) {
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
