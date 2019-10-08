package seedu.address.inventory.util;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.ui.InventoryUi;

import java.util.ArrayList;

/**
 *
 */
public class InventoryList {
    static ArrayList<Item> inventory;

    public InventoryList() {
        this.inventory = new ArrayList<Item>();
    }

    public InventoryList(ArrayList<Item> list) {
        this.inventory = list;
    }

    public Item get(int index) throws NoSuchIndexException {
        if (index >= inventory.size()) {
            throw new NoSuchIndexException(InventoryUi.NO_SUCH_INDEX_TRANSACTION);
        } else {
            return inventory.get(index);
        }
    }

    public void add(Item item) {
        inventory.add(item);
    }

    public void delete(int index) {
        inventory.remove(index);
    }

    public int size() {
        return inventory.size();
    }

    public void set(int i, Item item) {
        inventory.set(i, item);
    }
}
