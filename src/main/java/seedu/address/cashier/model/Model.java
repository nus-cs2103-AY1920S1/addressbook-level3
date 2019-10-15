package seedu.address.cashier.model;

import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.exception.NoSuchItemException;

/**
 * The API of the Model component of the cashier tab.
 */
public interface Model {

    /**
     * Adds the given {@code item}.
     */
    void addItem(Item item);

    /**
     * Finds the item according to the index in the Sales List.
     * @param index of the item to find
     * @return item of the index
     */
    Item findItemByIndex(int index);

    /**
     * Deletes the given item using the index.
     * The item must exist in the Sales List.
     */
    void deleteItem(int index);

    /**
     * Writes in the updated Inventory List into the Inventory text file.
     * @throws Exception
     */
    void writeInInventoryFile() throws Exception;

    /** Returns the Inventory List */
    InventoryList getInventoryList();

    /**
     * Replaces the given item {@code i} with {@code editedItem}.
     * {@code i} must exist in the Sales List.
     */
    void setItem(int i, Item editedItem) throws Exception;

    /**
     * Returns true if an item with the same attributes as {@code item} exists in the Inventory List.
     */
    boolean hasItemInInventory(Item item);

    /**
     * Returns true if the quantity keyed in is less than or equals to the quantity available in inventory.
     * Else, return false.
     *
     * @param description of the item to check
     * @param quantity of the item to check
     * @return true if sufficient quantity in inventory
     * @throws NoSuchItemException if there is no such item in the inventory
     */
    boolean hasSufficientQuantity(String description, int quantity)
            throws NoSuchItemException, seedu.address.cashier.model.exception.NoSuchItemException;

}

