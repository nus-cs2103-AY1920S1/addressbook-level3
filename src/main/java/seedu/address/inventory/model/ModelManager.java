package seedu.address.inventory.model;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.storage.StorageManager;
import seedu.address.inventory.util.InventoryList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private InventoryList inventoryList;
    private StorageManager storage;

    public ModelManager(InventoryList inventoryList) {
        this.inventoryList = inventoryList;
    }

    public ModelManager(StorageManager storage) {
        this.storage = storage;
        try {
            this.inventoryList = storage.getInventoryList();
        } catch (Exception e) {
            this.inventoryList = new InventoryList();
        }
    }

    public InventoryList getInventoryList() {
        return this.inventoryList;
    }

    @Override
    public void setItem(int i, Item editedItem) throws Exception {
        inventoryList.set(i - 1, editedItem);
    }

    /**
     * Returns true if an item with the same attributes as {@code item} exists in the Inventory List.
     */
    @Override
    public boolean hasItemInInventory(Item item) {
        for (int i = 0; i < inventoryList.size(); i++) {
            try {
                if (inventoryList.getItemByIndex(i).isSameItem(item)) {
                    return true;
                }
            } catch (Exception e) {
                return false;
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
    public void deleteItem(int index) {
        inventoryList.delete(index - 1);
    }

    @Override
    public void writeInInventoryFile() throws Exception {
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
            return false;
        } else {
            return true;
        }
    }

    /**
     * Updates the index of the items.
     * @throws NoSuchIndexException if the index is invalid
     */
    public void updateIndexes() throws NoSuchIndexException {
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
}
