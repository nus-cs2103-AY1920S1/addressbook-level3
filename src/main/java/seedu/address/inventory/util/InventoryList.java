package seedu.address.inventory.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.ui.InventoryMessages;

/**
 * Wraps all data of the inventory into a list.
 * Duplicates are allowed but are considered the same item when commands are done.
 */
public class InventoryList {
    private static ArrayList<Item> iList;

    public InventoryList() {
        this.iList = new ArrayList<Item>();
    }

    public InventoryList(ArrayList<Item> list) {
        this.iList = list;
    }

    public static Item getItemByIndex(int index) throws NoSuchIndexException {
        if (index >= iList.size()) {
            throw new NoSuchIndexException(InventoryMessages.NO_SUCH_INDEX_INVENTORY);
        } else {
            return iList.get(index);
        }
    }

    public ArrayList<Item> getiList() {
        return iList;
    }

    public int getIndex(String description) throws NoSuchItemException {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).getDescription().equalsIgnoreCase(description)) {
                return i;
            }
        }
        throw new NoSuchItemException(InventoryMessages.NO_SUCH_ITEM_INVENTORY);
    }

    public static Item getOriginalItem(String description) throws
            seedu.address.inventory.model.exception.NoSuchItemException {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).getDescription().equalsIgnoreCase(description)) {
                return iList.get(i);
            }
        }
        throw new seedu.address.inventory.model.exception.NoSuchItemException(InventoryMessages.NO_SUCH_ITEM_INVENTORY);
    }

    public void add(Item item) {
        iList.add(item);
    }

    public void delete(int index) {
        iList.remove(index);
    }

    /**
     * Checks if the InventoryList contains an item of the same description and category.
     */
    public boolean containsItem(Item item) {
        boolean containsItem = false;
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).isSameItem(item)) {
                containsItem = true;
            }
        }
        return containsItem;
    }

    public static int size() {
        return iList.size();
    }

    public void set(int i, Item item) {
        iList.set(i, item);
    }

    public void sortByDescription() {
        Collections.sort(iList, new SortByDescription());
    }

    public void sortByCategory() {
        Collections.sort(iList, new SortByCategory());
    }

    public void sortByQuantity() {
        Collections.sort(iList, new SortByQuantity());
    }

    public Stream<Item> stream() {
        return this.iList.stream();
    }

    public Item get(int i) {
        return iList.get(i);
    }


    public ArrayList<Item> getInventoryListInArrayList() {
        return this.iList;
    }

    /**
     * Resets the list
     */
    public void sortReset() {
        Collections.sort(iList, new ResetSort());

    }

    /**
     * Comparator to compare by the description in Item.
     */
    class SortByDescription implements Comparator<Item> {
        // Used for sorting in ascending order
        @Override
        public int compare(Item a, Item b) {
            return a.getDescription().toLowerCase().compareTo(b.getDescription().toLowerCase());
        }
    }

    /**
     * Comparator to compare by quantity in Item.
     */
    class SortByQuantity implements Comparator<Item> {
        // Used for sorting in ascending order
        @Override
        public int compare(Item a, Item b) {
            if (a.getQuantity() < b.getQuantity()) {
                return -1;
            } else if (a.getQuantity() == b.getQuantity()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * Comparator to compare by quantity in Item.
     */
    class SortByCategory implements Comparator<Item> {
        // Used for sorting in ascending order
        @Override
        public int compare(Item a, Item b) {
            return a.getCategory().toLowerCase().compareTo(b.getCategory().toLowerCase());
        }
    }


    /**
     * Comparator to compare by trueId in Item.
     */
    class ResetSort implements Comparator<Item> {
        @Override
        public int compare(Item a, Item b) {
            if (a.getTrueId() < b.getTrueId()) {
                return -1;
            } else if (a.getTrueId() == b.getTrueId()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        for (int i = 0; i < iList.size(); i++) {
            if (!iList.get(i).equals(((InventoryList) other).getiList().get(i))) {
                return false;
            }
        }
        return other == this // short circuit if same object
                || (other instanceof InventoryList // instanceof handles nulls
                && iList.equals(((InventoryList) other).getiList()));
    }

}
