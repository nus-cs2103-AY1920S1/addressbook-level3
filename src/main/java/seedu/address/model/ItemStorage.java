package seedu.address.model;

import seedu.address.commons.core.item.Item;
import seedu.address.model.item.ItemList;

/**
 * The central storage of all the items in the program.
 */
public class ItemStorage {
    private ItemList items = new ItemList();

    /**
     * Adds an item to the item list.
     * @param item the item to be added to the item list.
     */
    public void add(Item item) {
        items.add(item);
    }

    /**
     * Retrieve the item list.
     * @return the item list.
     */
    public ItemList getItems() {
        return this.items;
    }
}
