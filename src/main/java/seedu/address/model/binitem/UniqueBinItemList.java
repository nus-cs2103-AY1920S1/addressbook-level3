package seedu.address.model.binitem;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.binitem.exceptions.BinItemNotFoundException;
import seedu.address.model.binitem.exceptions.DuplicateBinItemException;

/**
 * List containing all items in the bin.
 */
public class UniqueBinItemList implements Iterable<BinItem> {

    private final ObservableList<BinItem> internalList = FXCollections.observableArrayList();
    private final ObservableList<BinItem> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent BinItem as the given argument.
     */
    public boolean contains(BinItem toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a BinItem to the list.
     * The BinItem must not already exist in the list.
     */
    public void add(BinItem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            // This should not happen on the user side since every bin item is created with a unique dateDeleted and
            // expiryDate. If this exception is thrown, there is a bug somewhere in the codebase.
            throw new DuplicateBinItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent BinItem from the list.
     * The BinItem must exist in the list.
     */
    public void remove(BinItem toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BinItemNotFoundException();
        }
    }

    /**
     * Replaces the BinItem {@code target} in the list with {@code editedBinItem}.
     * {@code target} must exist in the list.
     * The BinItem identity of {@code editedBinItem} must not be the same as another existing BinItem in the list.
     */
    public void setBinItem(BinItem target, BinItem editedBinItem) {
        requireAllNonNull(target, editedBinItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BinItemNotFoundException();
        }

        if (!target.equals(editedBinItem) && contains(editedBinItem)) {
            throw new DuplicateBinItemException();
        }

        internalList.set(index, editedBinItem);
    }

    /**
     * Replaces the contents of this list with {@code binItems}.
     * {@code binItems} must not contain duplicate BinItems.
     */
    public void setBinItems(List<BinItem> binItems) {
        requireAllNonNull(binItems);
        if (!binItemsAreUnique(binItems)) {
            throw new DuplicateBinItemException();
        }

        internalList.setAll(binItems);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<BinItem> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<BinItem> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueBinItemList // instanceof handles nulls
            && internalList.equals(((UniqueBinItemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code policies} contains only unique policies.
     */
    private boolean binItemsAreUnique(List<BinItem> binItems) {
        for (int i = 0; i < binItems.size() - 1; i++) {
            for (int j = i + 1; j < binItems.size(); j++) {
                if (binItems.get(i).equals(binItems.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
