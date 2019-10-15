package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.model.financetracker.exceptions.PurchaseNotFoundException;

/**
 * A list of purchases that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniquePurchaseList implements Iterable<Purchase> {

    private final ObservableList<Purchase> internalList = FXCollections.observableArrayList();
    private final ObservableList<Purchase> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Purchase toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePurchase);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Purchase toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Adds {@code Person} at a given {@code Index}.
     *
     * @param zeroBasedIndex Zero-based index to add {@code Person} to.
     * @param toAdd {@code Person} to be added.
     */
    public void add(int zeroBasedIndex, Purchase toAdd) {
        requireNonNull(toAdd);
        internalList.add(zeroBasedIndex, toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPurchase(Purchase target, Purchase editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PurchaseNotFoundException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Purchase toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PurchaseNotFoundException();
        }
    }

    public void setPurchases(UniquePurchaseList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
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
                || (other instanceof UniquePurchaseList // instanceof handles nulls
                && internalList.equals(((UniquePurchaseList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean purchasesAreUnique(List<Purchase> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePurchase(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
