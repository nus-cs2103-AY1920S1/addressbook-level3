package seedu.address.inventory.model;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.util.InventoryList;

public interface Model {

    void addItem(Item item);

    Item findItemByIndex(int index) throws NoSuchIndexException;

    void deleteItem(int index);

    void writeInInventoryFile() throws Exception;

    InventoryList getInventoryList();

    void setItem(int i, Item editedItem) throws Exception;

    boolean hasItemInInventory(Item item);

    boolean hasSufficientQuantity(String description, int quantity) throws NoSuchItemException;

    void updateIndexes() throws NoSuchIndexException;

    void sortByDescription();

    void sortByCategory();

    void sortByQuantity();

    void readInUpdatedList();

}
