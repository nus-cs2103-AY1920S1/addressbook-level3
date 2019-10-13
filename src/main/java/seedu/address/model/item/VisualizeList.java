package seedu.address.model.item;

import java.util.ArrayList;

import javafx.collections.ModifiableObservableListBase;
import seedu.address.commons.core.item.Item;

/**
 * An object to hold items. Parent class for TaskList, EventList and ReminderList.
 */
public abstract class VisualizeList extends ModifiableObservableListBase<Item> {
    protected ArrayList<Item> list;

    public VisualizeList() {
        this.list = new ArrayList<>();
    }

    /**
     * Add an item into the list.
     * @param item the item to be added into the list
     */
    public boolean add(Item item) {
        return list.add(item);
    }

    @Override
    public void doAdd(int index, Item item) {
        list.add(index, item);
    }

    /**
     * Get the list of the item list.
     * @return an ArrayList of all the items in the VisualizeList
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

    @Override
    public Item doSet(int index, Item item) {
        return list.set(index, item);
    }

    @Override
    public Item doRemove(int index) {
        return list.remove(index);
    }

    public abstract VisualizeList find(String searchString);

    /**
     * Helper function to find an item based on their description.
     * @param searchString the string to be search for within the description of an item
     * @param il the item list that will hold the items that contain the string within its description
     * @return the item list that was given with the found items added
     */
    protected VisualizeList find(String searchString, VisualizeList il) {
        for (Item i : list) {
            if (i.getItemDescription().getDescription().toLowerCase().contains(searchString.toLowerCase())) {
                il.add(i);
            }
        }
        return il;
    }

    public abstract VisualizeList deepCopy();

    /**
     * Helper function to return a deep copy of the list.
     * @param vl the list to be returned
     * @return
     */
    protected VisualizeList deepCopy(VisualizeList vl) {
        for (Item i : list) {
            try {
                vl.add(i.deepCopy());
            } catch (Exception e) {
                // not supposed to happen
            }
        }
        return vl;
    }

    public int indexOf(Item item) {
        return list.indexOf(item);
    }

    public Item setItem(int index, Item item) {
        return list.set(index, item);
    }

    /**
     * Returns the item at the index within the list.
     * @param index the index of the item within the list
     * @return the item that has that index in the list
     */
    public Item get(int index) {
        return list.get(index);
    }

    /**
     * The size of the list
     * @return the integer value of the size of the list
     */
    public int size() {
        return list.size();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            if (!(other instanceof VisualizeList)) {
                return false;
            }

            VisualizeList otherIl = (VisualizeList) other;
            return this.list.equals(otherIl.list);
        }
    }

    /**
     * Sorts the items in the list.
     * @return the item list in the sorted order
     */
    public abstract VisualizeList sort();


}
