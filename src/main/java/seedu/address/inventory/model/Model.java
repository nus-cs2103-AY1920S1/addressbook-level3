package seedu.address.inventory.model;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.util.InventoryList;

/**
 * The API of the Model component.
 */
public interface Model {

    void addItem(Item item);

    Item findItemByIndex(int index) throws NoSuchIndexException;

    int findIndexByDescription(String description) throws NoSuchItemException, NoSuchIndexException;

    void deleteItem(int index);

    void writeInInventoryFile() throws IOException, NoSuchIndexException;

    void setItem(int i, Item editedItem);

    boolean hasItemInInventory(Item item);

    void readInUpdatedList();

    ArrayList<Item> getInventoryListInArrayList();

    InventoryList getInventoryList();

    void sortByQuantity();

    void sortByDescription();

    void sortByCategory();

    void sortReset();

    //InventoryList getInventoryList();

    void updateIndexes();

    boolean equals(Object obj);

}
