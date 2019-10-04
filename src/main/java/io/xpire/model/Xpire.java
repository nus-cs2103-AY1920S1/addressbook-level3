package io.xpire.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import io.xpire.model.item.Item;
import io.xpire.model.item.UniqueItemList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the expiry-date-tracker level
 * Duplicates are not allowed (by .isSameItem comparison)
 */
public class ExpiryDateTracker implements ReadOnlyExpiryDateTracker {

    private final UniqueItemList items;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        items = new UniqueItemList();
    }

    public ExpiryDateTracker() {}

    /**
     * Creates an ExpiryDateTracker using the Items in the {@code toBeCopied}
     */
    public ExpiryDateTracker(ReadOnlyExpiryDateTracker toBeCopied) {
        this();
        resetData(toBeCopied);
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
     * Resets the existing data of this {@code ExpiryDateTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyExpiryDateTracker newData) {
        requireNonNull(newData);
        setItems(newData.getItemList());
    }

    //// person-level operations

    /**
     * Returns true if an item with the same identity as {@code item} exists in the tracker.
     */
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return items.contains(item);
    }

    /**
     * Adds a item to the tracker.
     * The item must not already exist in the tracker.
     */
    public void addItem(Item p) {
        items.add(p);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the tracker.
     * The item identity of {@code editedItem} must not be the same as another existing item in the tracker.
     */
    public void setItem(Item target, Item editedItem) {
        requireNonNull(editedItem);
        items.setItem(target, editedItem);
    }

    /**
     * Removes {@code key} from this {@code ExpiryDateTracker}.
     * {@code key} must exist in the tracker.
     */
    public void removeItem(Item key) {
        items.remove(key);
    }


    //// util methods

    @Override
    public String toString() {
        return items.asUnmodifiableObservableList().size() + " items";
        // TODO: refine later
    }

    @Override
    public ObservableList<Item> getItemList() {
        return items.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpiryDateTracker // instanceof handles nulls
                && items.equals(((ExpiryDateTracker) other).items));
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
