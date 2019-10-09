package seedu.address.inventory.model;

import seedu.address.inventory.model.exception.NoSuchIndexException;
<<<<<<< HEAD
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.util.InventoryList;
<<<<<<< HEAD
=======
import seedu.address.inventory.util.InventoryList;
import seedu.address.transaction.util.TransactionList;
>>>>>>> Implemented some Inventory classes
=======
import seedu.address.transaction.util.TransactionList;
>>>>>>> fc0be654de9206fb671410f8894ac898fe42746c

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
