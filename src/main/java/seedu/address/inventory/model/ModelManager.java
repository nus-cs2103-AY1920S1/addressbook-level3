package seedu.address.inventory.model;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.storage.StorageManager;
import seedu.address.inventory.util.InventoryList;


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

    @Override
    public InventoryList getInventoryList() {
        return this.inventoryList;
    }

    @Override
    public void setItem(Item itemToEdit, Item editedItem) throws Exception {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).equals(itemToEdit)) {
                inventoryList.set(i, editedItem);
            }
        }
    }

    @Override
    public boolean hasItemInInventory(Item item) {
        for (int i = 0 ; i < inventoryList.size(); i++) {
            try {
                if (inventoryList.getItemByIndex(i).equals(item)) {
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
        Item item = inventoryList.get(index - 1);
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

    @Override
    public boolean hasSufficientQuantity(Item item, int quantity) {
        boolean hasSufficientQuantity = true;
        if (item.getQuantity() < quantity || !hasItem(item)) {
            hasSufficientQuantity = false;
        }
        return hasSufficientQuantity;
    }

    @Override
    public void updateInventoryList() throws Exception {

    }
}
