package seedu.elisa.model.item;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ModifiableObservableListBase;
import seedu.elisa.commons.core.item.Item;

/**
 * An object to hold items. Parent class for TaskList, EventList and ReminderList.
 */
public abstract class VisualizeList extends ModifiableObservableListBase<Item> {
    protected ArrayList<Item> list;

    public VisualizeList() {
        this.list = new ArrayList<>();
    }

    public VisualizeList(List<Item> list) {
        this();
        this.list.addAll(list);
    }

    /**
     * Add an item into the list. The item will not be added if it is already in the list
     * or it does not belong to the list.
     * @param item the item to be added into the list
     */
    public boolean add(Item item) {
        if (hasItem(item) || !belongToList(item)) {
            return false;
        } else {
            return super.add(item);
        }
    }

    /**
     * add item into specified index
     * */

    public void addToIndex(int targetIndex, Item item) {
        if (hasItem(item) || !belongToList(item)) {
            return;
        } else {
            super.add(targetIndex, item);
        }
    }

    /**
     * Check if the list contains the item.
     * @param item the item to be checked for.
     * @return true if the item is in the list, else return false.
     */
    public boolean hasItem(Item item) {
        return super.contains(item);
    }

    @Override
    public void doAdd(int index, Item item) {
        list.add(index, item);
    }

    @Override
    public Item doSet(int index, Item item) {
        return list.set(index, item);
    }

    @Override
    public Item doRemove(int index) {
        return list.remove(index);
    }

    /**
     * Get the list of the item list.
     * @return an ArrayList of all the items in the VisualizeList
     */
    public ArrayList<Item> getList() {
        return this.list;
    }

    public abstract VisualizeList find(String[] searchString);

    /**
     * Helper function to find an item based on their description.
     * @param searchStrings an array of string to be search for within the description of an item
     * @param il the item list that will hold the items that contain the string within its description
     * @return the item list that was given with the found items added
     */
    protected VisualizeList find(String[] searchStrings, VisualizeList il) {
        for (String searchString : searchStrings) {
            for (Item i : list) {
                if (il.contains(i)) {
                    continue;
                }

                if (i.getItemDescription().getDescription()
                        .toLowerCase().contains(searchString.toLowerCase())) {
                    il.add(i);
                }
            }
        }
        return il;
    }

    /**
     * Deep copy a list.
     * @return a list with all the items within it a deep copy of their original item.
     */
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
        return super.indexOf(item);
    }

    public Item setItem(int index, Item item) {
        return super.set(index, item);
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
     * The size of the list.
     * @return the integer value of the size of the list
     */
    @Override
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

    public void clear() {
        super.clear();
    }

    /**
     * Checks if an item belongs to this list.
     * @param item the item to be check
     * @return a boolean true if the item belong to this list and false otherwise
     */
    public abstract boolean belongToList(Item item);

}
