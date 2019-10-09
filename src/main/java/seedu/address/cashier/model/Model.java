package seedu.address.cashier.model;

import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.exception.NoSuchItemException;

public interface Model {

    void addItem(Item item);

    Item findItemByIndex(int index) throws NoSuchIndexException;

    void deleteItem(int index);

    void writeInInventoryFile() throws Exception;

    InventoryList getInventoryList();

    void setItem(int i, Item editedItem) throws Exception;

    boolean hasItemInInventory(Item item);

    boolean hasSufficientQuantity(String description, int quantity) throws NoSuchItemException;

}

