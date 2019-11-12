package io.xpire.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import io.xpire.model.item.Item;
import io.xpire.model.item.SortedUniqueReplenishItemList;
import io.xpire.model.item.sort.MethodOfSorting;
import javafx.collections.ObservableList;

/**
 * Wraps all items to be replenished at this ReplenishList level.
 * Duplicates are not allowed (by .isSameItem comparison).
 */
public class ReplenishList implements ReadOnlyListView<Item> {

    private final Logger logger = LogsCenter.getLogger(ReplenishList.class);
    private final SortedUniqueReplenishItemList items = new SortedUniqueReplenishItemList();

    public ReplenishList() {

    }

    public ReplenishList(ReadOnlyListView toBeCopied) {
        this();
        this.resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the item list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setItems(List<Item> items) {
        this.items.setItems(items);
    }


    /**
     * Resets the existing data of this {@code ReplenishList} with {@code newData}.
     */
    @SuppressWarnings("unchecked")
    public void resetData(ReadOnlyListView newData) {
        requireNonNull(newData);
        try {
            this.setItems(newData.getItemList());
        } catch (ClassCastException e) {
            this.logger.warning("Wrong item type for Replenish");
        }
    }

    /**
     * Returns true if an item with the same identity as {@code item} exists in the list.
     */
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return this.items.contains(item);
    }

    /**
     * Adds a item to list.
     * The item must not already exist in list.
     */
    public void addItem(Item item) {
        this.items.add(item);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the list.
     * The item identity of {@code editedItem} must not be the same as another existing item in list.
     */
    public void setItem(Item target, Item editedItem) {
        requireNonNull(editedItem);
        this.items.setItem(target, editedItem);
    }

    /**
     * Removes {@code key} from this {@code ReplenishList}.
     * {@code key} must exist in the list.
     */
    public void removeItem(Item key) {
        this.items.remove(key);
    }

    /**
     * Set method of sorting.
     */
    public void setMethodOfSorting(MethodOfSorting<Item> method) {
        this.items.setMethodOfSorting(method);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.items.asUnmodifiableObservableList().size() + " items");
        this.items.asUnmodifiableObservableList().forEach(sb::append);
        return sb.toString();
    }

    @Override
    public ObservableList<Item> getItemList() {
        return this.items.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ReplenishList)) {
            return false;
        } else {
            ReplenishList other = (ReplenishList) obj;
            return this.getItemList().equals(other.getItemList());
        }
    }

    @Override
    public int hashCode() {
        return this.items.hashCode();
    }
}
