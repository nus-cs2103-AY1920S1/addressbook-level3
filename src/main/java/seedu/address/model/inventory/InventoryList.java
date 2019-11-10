package seedu.address.model.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.inventory.exceptions.DuplicateInventoryException;
import seedu.address.model.inventory.exceptions.InventoryNotFoundException;
import seedu.address.model.inventory.exceptions.InventoryNotRemovableException;

/**
 * Abstraction of a list containing trips, backed by ConsecutiveOccurenceList.
 */
public class InventoryList {

    //public final ObservableList<Expenditure> internalList = FXCollections.observableArrayList();

    private HashMap<String, Integer> eventInstances;

    public final ObservableList<Inventory> list = FXCollections.observableArrayList(inventory ->
            new Observable[] {
                    inventory.getIsDoneProperty()
            });

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

    public void addAll (InventoryList listToAdd) throws DuplicateInventoryException {
        requireNonNull(listToAdd);

        for (Inventory item : listToAdd.getList()) {
            try {
                add(item);
            } catch (DuplicateInventoryException e) {

            }
        }

    }

    public void addEventInventoryList (List<Inventory> eventInventoryList) {
        requireNonNull(eventInventoryList);

        for (Inventory item : eventInventoryList) {

            Optional<Inventory> optional = this.list.stream().filter(item::isSameInventory).findAny();

            if (optional.isPresent()) {

                Inventory existingItem = optional.get();


                //This item is already in a different event
                if (existingItem.getEventInstances() > 0) {
                    existingItem.setEventInstances(existingItem.getEventInstances() + 1);
                } else {
                    existingItem.setIsRemovable(false);
                    existingItem.setEventInstances(1);
                }

            } else {
                item.setIsRemovable(false);
                item.setEventInstances(1);
                this.list.add(item);
            }

        }
    }

    public void removeEventInventoryList (List<Inventory> eventInventoryList) throws InventoryNotFoundException {
        requireNonNull(eventInventoryList);

        System.out.println("removeEventInventoryList called with eventIL " + eventInventoryList.size());

        for (Inventory item : eventInventoryList) {

            Optional<Inventory> optional = this.list.stream().filter(item::isSameInventory).findAny();

            if (optional.isPresent()) {

                Inventory existingItem = optional.get();

                System.out.println("EXISTINGITEM IS " + existingItem.getEventInstances());

                existingItem.setEventInstances(existingItem.getEventInstances() - 1);

                if (existingItem.getEventInstances() == 0) {
                    remove(existingItem);
                }

                System.out.println("NOW IT IS " + existingItem.getEventInstances());

            }

        }
    }

    public List<Inventory> getList () {
        return this.list;
    }

    public void set(List<Inventory> occurrences) {
        requireAllNonNull(occurrences);

        /*
        if (!expendituresAreUnique(occurrences)) {
            throw new DuplicateExpenditureException();
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
            throw new InventoryNotFoundException();
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
            throw new InventoryNotFoundException();
        }
    }

    /**
     * To remove an item from the inventory list
     * @param index Index of item to remove
     * @throws InventoryNotFoundException
     */
    public void removeFromInventoryPage(Index index) throws InventoryNotFoundException, InventoryNotRemovableException {

        try {
            Inventory item = list.get(index.getZeroBased());

            if (item.getEventInstances() == 0) {
                list.remove(index.getZeroBased());
            } else {
                throw new InventoryNotRemovableException();
            }

        } catch (IndexOutOfBoundsException e) {
            throw new InventoryNotFoundException();
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InventoryList // instanceof handles nulls
                && list.equals(((InventoryList) other).list));
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    public int getSize () {
        return this.list.size();
    }

    public void markNotDone (Index index) throws InventoryNotFoundException {

        try {
            Inventory inventory = list.get(index.getZeroBased());

            inventory.setIsDone(false);

        } catch (IndexOutOfBoundsException e) {
            throw new InventoryNotFoundException();
        }
    }

    public void markDone (Index index) throws InventoryNotFoundException {

        try {
            Inventory inventory = list.get(index.getZeroBased());

            inventory.setIsDone(true);

        } catch (IndexOutOfBoundsException e) {
            throw new InventoryNotFoundException();
        }

    }

}
