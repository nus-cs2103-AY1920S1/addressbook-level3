package seedu.address.inventory.model;

import seedu.address.inventory.model.exception.NoSuchIndexException;
<<<<<<< HEAD
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.util.InventoryList;
=======
import seedu.address.inventory.util.InventoryList;
import seedu.address.transaction.util.TransactionList;
>>>>>>> Implemented some Inventory classes

public interface Model {

    void addItem(Item item);

    Item findItemByIndex(int index) throws NoSuchIndexException;

    void deleteItem(int index);

    void writeInInventoryFile() throws Exception;

    InventoryList getInventoryList();

<<<<<<< HEAD
    void setItem(int i, Item editedItem) throws Exception;

    boolean hasItemInInventory(Item item);

    boolean hasSufficientQuantity(String description, int quantity) throws NoSuchItemException;

=======
    void setItem(Item itemToEdit, Item editedItem) throws Exception;

    boolean hasItem(Item item);

    boolean hasSufficientQuantity(Item item, int quantity);

    void updateInventoryList() throws Exception;
>>>>>>> Implemented some Inventory classes
}
