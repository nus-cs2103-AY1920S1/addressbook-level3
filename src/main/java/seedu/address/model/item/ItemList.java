package seedu.address.model.item;

import java.util.ArrayList;

/**
 * An object to hold items. Parent class for TaskList, EventList and ReminderList.
 */
public class ItemList {
    protected ArrayList<Item> list;

    public ItemList() {
        this.list = new ArrayList<>();
    }

    /**
     * Add an item into the list.
     * @param item the item to be added into the list
     */
    public void add(Item item) {
        list.add(item);
    }

    /**
     * Get the list of the item list.
     * @return an ArrayList of all the items in the ItemList
     */
    public ArrayList<Item> getList() {
        return this.list;
    }

    /**
     * Removes an item from the list base on it's index.
     * @param index the integer value of the index of the item to be removed
     * @return the item that is removed from this operation
     */
    public Item remove(int index) {
        return list.remove(index);
    }

    /**
     * Removes an item from the list.
     * @param item the item to be removed from the list
     */
    public void remove(Item item) {
        list.remove(item);
    }

    /**
     * Sorts the items in the list by their lexicographical order.
     * @return the item list in the sorted order
     */
    public ItemList sort() {
        ItemList il = new ItemList();
        for (Item item : list) {
            il.add(item);
        }

        il.list.sort((item1, item2) -> item1.getDescription().compareTo(item2.getDescription()));
        return il;
    }

    /**
     * Main method to find a search string.
     * @param searchString the string to be searched for within the description
     * @return a new ItemList containing all the items that match the criteria.
     */
    public ItemList find(String searchString) {
        ItemList il = new ItemList();
        return find(searchString, il);
    }

    /**
     * Helper function to find an item based on their description.
     * @param searchString the string to be search for within the description of an item
     * @param il the item list that will hold the items that contain the string within its description
     * @return the item list that was given with the found items added
     */
    protected ItemList find(String searchString, ItemList il) {
        for (Item i : list) {
            if (i.getDescription().toLowerCase().contains(searchString.toLowerCase())) {
                il.add(i);
            }
        }
        return il;
    }
}
