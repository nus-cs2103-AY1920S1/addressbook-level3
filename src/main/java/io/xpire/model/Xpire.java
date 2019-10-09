package io.xpire.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import io.xpire.model.item.Item;
import io.xpire.model.item.UniqueItemList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at xpire level
 * Duplicates are not allowed (by .isSameItem comparison)
 */
public class Xpire implements ReadOnlyXpire {

    private final UniqueItemList items = new UniqueItemList();

    public Xpire() {}

    /**
     * Creates a Xpire object using the Items in the {@code toBeCopied}
     */
    public Xpire(ReadOnlyXpire toBeCopied) {
        this();
        this.resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the item list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setItems(List<Item> items) {
        this.items.setItems(items);
    }

    /**
     * Resets the existing data of this {@code Xpire} with {@code newData}.
     */
    public void resetData(ReadOnlyXpire newData) {
        requireNonNull(newData);
        this.setItems(newData.getItemList());
    }

    /**
     * Returns true if an item with the same identity as {@code item} exists in xpire.
     */
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return this.items.contains(item);
    }

    /**
     * Adds a item to xpire.
     * The item must not already exist in xpire.
     */
    public void addItem(Item item) {
        this.items.add(item);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in xpire.
     * The item identity of {@code editedItem} must not be the same as another existing item in xpire.
     */
    public void setItem(Item target, Item editedItem) {
        requireNonNull(editedItem);
        this.items.setItem(target, editedItem);
    }

    /**
     * Removes {@code key} from this {@code Xpire}.
     * {@code key} must exist in xpire.
     */
    public void removeItem(Item key) {
        this.items.remove(key);
    }


    //// util methods

    @Override
    public String toString() {
        return this.items.asUnmodifiableObservableList().size() + " items";
        // TODO: refine later
    }

    @Override
    public ObservableList<Item> getItemList() {
        return this.items.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Xpire)) {
            return false;
        } else {
            Xpire other = (Xpire) obj;
            return this.items.equals(other.items);
        }
    }

    @Override
    public int hashCode() {
        return this.items.hashCode();
    }
}
