package seedu.address.model.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.inventory.exceptions.DuplicateInventoryException;
import seedu.address.model.inventory.exceptions.InventoryNotFoundException;

/**
 * A list of inventories that enforces uniqueness between its elements and does not allow nulls.
 * A inventory is considered unique by comparing using {@code Inventory#isSameInventory(Inventory)}.
 * As such, adding and updating of persons uses Inventory#isSameInventory(Inventory) for equality so as to ensure that
 * the inventory being added or updated is unique in terms of identity in the UniqueInventoryList. However, the
 * removal of a inventory uses Inventory#equals(Object) so as to ensure that the inventory with exactly the same fields
 * will be removed.
 * Supports a minimal set of list operations.
 *
 */
public class UniqueInventoryList implements Iterable<Inventory> {
    private final ObservableList<Inventory> internalList = FXCollections.observableArrayList();
    private final ObservableList<Inventory> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Inventory as the given argument.
     */
    public boolean contains(Inventory toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameInventory);
    }

    /**
     * Adds a Inventory to the list.
     * The Inventory must not already exist in the list.
     */
    public void add(Inventory toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateInventoryException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the inventory {@code target} in the list with {@code editedInventory}.
     * {@code target} must exist in the list.
     * The inventory identity of {@code editedInventory} must not be the same as another existing task in the list.
     */
    public void setInventory(Inventory target, Inventory editedInventory) {
        requireAllNonNull(target, editedInventory);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new InventoryNotFoundException();
        }

        if (!target.isSameInventory(editedInventory) && contains(editedInventory)) {
            throw new DuplicateInventoryException();
        }

        internalList.set(index, editedInventory);
    }

    /**
     * Removes the equivalent Inventory from the list.
     * The inventory must exist in the list.
     */
    public void remove(Inventory toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new InventoryNotFoundException();
        }
    }

    public void setInventories(UniqueInventoryList replacement) {
        requireNonNull(replacement);
        internalList.clear();
        internalList.addAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setInventories(List<Inventory> inventories) {
        requireAllNonNull(inventories);
        if (!inventoriesAreUnique(inventories)) {
            throw new DuplicateInventoryException();
        }

        internalList.clear();
        internalList.addAll(inventories);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Inventory> asUnmodifiableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Inventory> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueInventoryList // instanceof handles nulls
                && internalList.equals(((UniqueInventoryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code inventories} contains only unique tasks.
     */
    private boolean inventoriesAreUnique(List<Inventory> inventories) {
        for (int i = 0; i < inventories.size() - 1; i++) {
            for (int j = i + 1; j < inventories.size(); j++) {
                if (inventories.get(i).isSameInventory(inventories.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
