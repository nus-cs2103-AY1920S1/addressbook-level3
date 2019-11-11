package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.transaction.exceptions.DuplicateTransactionException;
import seedu.address.model.transaction.exceptions.TransactionNotFoundException;

/**
 * A list of ledger operations that enforces uniqueness between its elements and does not allow nulls.
 * A ledger operation is considered unique by comparing using
 * {@code LedgerOperation#isSameLedgerOperation(LedgerOperation)}.
 * As such, adding and updating of ledger operations uses LedgerOperation#isSameLedgerOperation(LedgerOperation)
 * for equality so as to ensure that the ledger operation being added or updated is unique in terms of identity in the
 * UniqueLedgerOperationList. However, the removal of a LedgerOperation uses LedgerOperation#equals(Object) so
 * as to ensure that the ledger operation with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see LedgerOperation#isSameLedgerOperation(LedgerOperation)
 */
public class UniqueLedgerOperationList implements Iterable<LedgerOperation> {

    private final ObservableList<LedgerOperation> internalList = FXCollections.observableArrayList();
    private final ObservableList<LedgerOperation> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent ledger operations as the given argument.
     */
    public boolean contains(LedgerOperation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a ledger operation to the list.
     * The ledger operation must not already exist in the list.
     */
    public void add(LedgerOperation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTransactionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the ledger operation {@code ledgerOperationTarget} in the list with {@code ledgerOperationEdit}.
     * {@code ledgerOperationTarget} must exist in the list.
     * The ledger operation identity of {@code ledgerOperationEdit} must not be the same as
     * another existing ledger operation in the list.
     */
    public void set(LedgerOperation ledgerOperationTarget, LedgerOperation ledgerOperationEdit) {
        requireAllNonNull(ledgerOperationTarget, ledgerOperationEdit);

        int index = internalList.indexOf(ledgerOperationTarget);
        if (index == -1) {
            throw new TransactionNotFoundException();
        }

        if (!ledgerOperationTarget.equals(ledgerOperationEdit) && contains(ledgerOperationEdit)) {
            throw new DuplicateTransactionException();
        }

        internalList.set(index, ledgerOperationEdit);
    }

    /**
     * Removes the equivalent ledger operation from the list.
     * The ledger operation must exist in the list.
     */
    public void remove(LedgerOperation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TransactionNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code ledgerOperations}.
     * {@code internalList} must not contain duplicate ledgerOperations.
     */
    public void setLedgerOperations(List<LedgerOperation> ledgerOperations) {
        requireAllNonNull(ledgerOperations);
        if (!ledgerOperationsAreUnique(ledgerOperations)) {
            throw new DuplicateTransactionException();
        }

        internalList.setAll(ledgerOperations);
    }

    public void setLedgerOperations(UniqueLedgerOperationList ledgerOperations) {
        setLedgerOperations(ledgerOperations.internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
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
     * Returns true if {@code ledgerOperations} contains only unique ledgerOperations.
     */
    private boolean ledgerOperationsAreUnique(List<LedgerOperation> ledgerOperations) {
        for (int i = 0; i < ledgerOperations.size() - 1; i++) {
            for (int j = i + 1; j < ledgerOperations.size(); j++) {
                if (ledgerOperations.get(i).equals(ledgerOperations.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
