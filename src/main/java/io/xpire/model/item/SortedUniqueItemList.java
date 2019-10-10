package io.xpire.model.item;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import io.xpire.commons.util.CollectionUtil;
import io.xpire.model.item.exceptions.DuplicateItemException;
import io.xpire.model.item.exceptions.ItemNotFoundException;
import io.xpire.model.item.sort.MethodOfSorting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;


/**
 * A list of items that enforces uniqueness between its elements and does not allow nulls.
 * An item is considered unique by comparing using {@code Item#isSameItem(Item)}. As such, adding and updating of
 * items uses Item#isSameItem(Item) for equality so as to ensure that the item being added or updated is
 * unique in terms of identity in the SortedUniqueItemList. However, the removal of a item uses Item#equals(Object) so
 * as to ensure that the item with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Item#isSameItem(Item)
 */
public class SortedUniqueItemList implements Iterable<Item> {
    private final ObservableList<Item> internalList = FXCollections.observableArrayList();
    private final SortedList<Item> sortedInternalList = new SortedList<>(internalList);
    private final ObservableList<Item> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(this.sortedInternalList);
    private MethodOfSorting methodOfSorting = new MethodOfSorting("name");

    /**
     * Returns true if the list contains an equivalent item as the given argument.
     */
    public boolean contains(Item toCheck) {
        requireNonNull(toCheck);
        return this.internalList.stream().anyMatch(toCheck::isSameItem);
    }

    /**
     * Adds an item to the list.
     * The item must not already exist in the list.
     */
    public void add(Item toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        this.internalList.add(toAdd);
        methodOfSorting = new MethodOfSorting("name");
    }

    /**
     * Replaces the item { @code target} in the list with {@code editedItem}.
     * {@code target} must exist in the list.
     * The item identity of {@code editedItem} must not be the same as another existing item in the list.
     */
    public void setItem(Item target, Item editedItem) {
        CollectionUtil.requireAllNonNull(target, editedItem);

        int index = this.internalList.indexOf(target);
        if (index == -1) {
            throw new ItemNotFoundException();
        }

        if (!target.isSameItem(editedItem) && contains(editedItem)) {
            throw new DuplicateItemException();
        }

        this.internalList.set(index, editedItem);
    }

    /**
     * Removes the equivalent item from the list.
     * The item must exist in the list.
     */
    public void remove(Item toRemove) {
        requireNonNull(toRemove);
        if (!this.internalList.remove(toRemove)) {
            throw new ItemNotFoundException();
        }
    }

    public void setItems(SortedUniqueItemList replacement) {
        requireNonNull(replacement);
        this.internalList.setAll(replacement.sortedInternalList);
    }

    /**
     * Replaces the contents of this list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setItems(List<Item> items) {
        CollectionUtil.requireAllNonNull(items);
        if (!itemsAreUnique(items)) {
            throw new DuplicateItemException();
        }
        this.internalList.setAll(items);
    }

    /**
     * Set method of sorting.
     */
    public void setMethodOfSorting(MethodOfSorting method) {
        this.methodOfSorting = method;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Item> asUnmodifiableObservableList() {
        this.sortedInternalList.setComparator(methodOfSorting.getComparator());
        return this.internalUnmodifiableList;
    }

    @Override
    public Iterator<Item> iterator() {
        return this.sortedInternalList.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof SortedUniqueItemList)) {
            return false;
        } else {
            SortedUniqueItemList other = (SortedUniqueItemList) obj;
            return this.internalList.equals(other.internalList);
        }
    }

    @Override
    public int hashCode() {
        return this.internalList.hashCode();
    }

    /**
     * Returns true if {@code items} contains only unique items.
     */
    private boolean itemsAreUnique(List<Item> items) {
        return items.size() == items.stream().distinct().count();
    }
}
