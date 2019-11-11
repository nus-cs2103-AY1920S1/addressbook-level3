package seedu.address.inventory.model;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.storage.Storage;
import seedu.address.inventory.util.InventoryList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model, ReadInUpdatedListOnlyModel {
    private InventoryList inventoryList;
    private Storage storage;

    public ModelManager(InventoryList inventoryList) {
        this.inventoryList = inventoryList;
    }

    public ModelManager(Storage storage) {
        this.storage = storage;
        try {
            this.inventoryList = storage.getInventoryList();
        } catch (IOException e) {
            this.inventoryList = new InventoryList();
        }
    }

    @Override
    public InventoryList getInventoryList() {
        return this.inventoryList;
    }

    @Override
    public ArrayList<Item> getInventoryListInArrayList() {
        return this.inventoryList.getInventoryListInArrayList();
    }

    @Override
    public void setItem(int i, Item editedItem) {
        inventoryList.set(i - 1, editedItem);
    }

    /**
     * Returns true if an item with the same attributes as {@code item} exists in the Inventory List.
     */
    @Override
    public boolean hasItemInInventory(Item item) {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).isSameItem(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addItem(Item item) {
        inventoryList.add(item);
    }

    @Override
    public Item findItemByIndex(int index) throws NoSuchIndexException {
        Item item = inventoryList.getItemByIndex(index - 1);
        return item;
    }

    @Override
    public int findIndexByDescription(String description) throws NoSuchItemException, NoSuchIndexException {
        int index = inventoryList.getIndex(description) + 1;
        return index;
    }

    @Override
    public void deleteItem(int index) {
        inventoryList.delete(index - 1);
    }

    @Override
    public void writeInInventoryFile() throws IOException, NoSuchIndexException {
        storage.writeFile(inventoryList);
    }

    /**
     * Returns true if the quantity keyed in is less than or equals to the quantity available in inventory.
     * Else, return false.
     *
     * @param description of the item to check
     * @param quantity of the item to check
     * @return true if sufficient quantity in inventory
     * @throws NoSuchItemException if there is no such item in the inventory
     */
    public boolean hasSufficientQuantity(String description, int quantity) throws NoSuchItemException {
        if (inventoryList.getOriginalItem(description).getQuantity() > quantity) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates the index of the items.
     */
    public void updateIndexes() {
        for (int i = 0; i < inventoryList.size(); i++) {
            Item item = inventoryList.get(i);
            item.setId(i + 1);
        }
    }

    public void sortByDescription() {
        inventoryList.sortByDescription();
    }

    public void sortByCategory() {
        inventoryList.sortByCategory();
    }

    public void sortByQuantity() {
        inventoryList.sortByQuantity();
    }

    public void sortReset() {
        inventoryList.sortReset();
    }

    /**
     * Updates the recent inventory list from the data file.
     */
    public void readInUpdatedList() {
        try {
            this.inventoryList = storage.getInventoryList();
        } catch (Exception e) {
            this.inventoryList = new InventoryList();
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModelManager // instanceof handles nulls
                && inventoryList.equals(((ModelManager) other).getInventoryList()));
    }

}
