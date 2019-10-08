package seedu.address.inventory.model;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.util.InventoryList;
import seedu.address.transaction.util.TransactionList;

public interface Model {

    void addItem(Item item);

    Item findItemByIndex(int index) throws NoSuchIndexException;

    void deleteItem(int index);

    void writeInInventoryFile() throws Exception;

    InventoryList getInventoryList();

    void setItem(Item itemToEdit, Item editedItem) throws Exception;

    boolean hasItemInInventory(Item item);

    boolean hasSufficientQuantity(Item item, int quantity);

    void updateInventoryList() throws Exception;
}
