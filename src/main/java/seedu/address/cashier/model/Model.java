package seedu.address.cashier.model;

import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.exception.NoSuchItemException;

public interface Model {

    void addItem(Item item);

    Item findItemByIndex(int index) throws NoSuchIndexException;

    void deleteItem(int index);

    InventoryList getInventoryList();

    boolean hasSufficientQuantity(Item i, int quantity) throws NoSuchItemException;

    void writeInInventoryFile() throws Exception;

    //void setTransaction(Transaction transactionToEdit, Transaction editedTransaction) throws Exception;

    boolean hasItemInInventory(Item item);

    void updateInventoryList() throws Exception;

}

