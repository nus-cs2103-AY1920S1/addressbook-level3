package seedu.address.model.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.inventory.exceptions.DuplicateInventoryException;
import seedu.address.model.inventory.exceptions.InventoryNotFoundException;

/**
 * Abstraction of a list containing trips, backed by ConsecutiveOccurenceList.
 */
public class InventoryList {

    //public final ObservableList<Expense> internalList = FXCollections.observableArrayList();

    public final ObservableList<Inventory> list = FXCollections.observableArrayList();

    public final ObservableList<Inventory> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(list);

    /*
    public InventoryList() {
        this.list = new ArrayList<Inventory>();
    }*/

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Inventory> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Check if an invetory item is inside the list
     * @param toCheck Inventory item to check
     * @return True if it is inside (otherwise false)
     */
    public boolean contains(Inventory toCheck) {
        requireNonNull(toCheck);
        return list.stream().anyMatch(toCheck::isSameInventory);
    }

    /**
     * To add an item to the inventory list
     * @param toAdd Item to add
     * @throws DuplicateInventoryException
     */
    public void add(Inventory toAdd) throws DuplicateInventoryException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateInventoryException();
        }

        list.add(toAdd);
    }

    public List<Inventory> getList () {
        return this.list;
    }

    public void set(List<Inventory> occurrences) {
        requireAllNonNull(occurrences);

        /*
        if (!expensesAreUnique(occurrences)) {
            throw new DuplicateExpenseException();
        }*/

        list.setAll(occurrences);
    }

    /**
     * To remove an item from the Inventory List
     * @param toRemove Item to remove
     * @throws InventoryNotFoundException
     */
    public void remove(Inventory toRemove) throws InventoryNotFoundException {
        requireNonNull(toRemove);
        if (!list.remove(toRemove)) {
            throw new InventoryNotFoundException("ERROR: INVENTORY ITEM NOT FOUND");
        }
    }

    /**
     * To remove an item from the inventory list
     * @param index Index of item to remove
     * @throws InventoryNotFoundException
     */
    public void remove(Index index) throws InventoryNotFoundException {

        try {
            list.remove(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new InventoryNotFoundException("ERROR: INVENTORY ITEM NOT FOUND");
        }
    }

}
