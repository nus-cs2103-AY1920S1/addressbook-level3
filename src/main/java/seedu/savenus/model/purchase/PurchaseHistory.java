package seedu.savenus.model.purchase;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.savenus.model.purchase.exceptions.PurchaseNotFoundException;

/**
 *  A list of purchases made.
 *
 *  Supports a minimal set of list operations.
 */
public class PurchaseHistory implements Iterable<Purchase> {
    private final ObservableList<Purchase> internalList = FXCollections.observableArrayList();
    private final ObservableList<Purchase> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a {@code Purchase} to the list.
     */
    public void add(Purchase toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent {@code Purchase} from the list.
     * The {@code Purchase} must exist in the list.
     */
    public void remove(Purchase toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PurchaseNotFoundException();
        }
    }

    /**
     * Replaces the current {@code PurchaseList} with a new {@code PurchaseList}.
     */
    public void setPurchases(PurchaseHistory replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Purchases}.
     */
    public void setPurchases(List<Purchase> purchases) {
        requireAllNonNull(purchases);
        internalList.setAll(purchases);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Purchase> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Purchase> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PurchaseHistory // instanceof handles nulls
                && internalList.equals(((PurchaseHistory) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
