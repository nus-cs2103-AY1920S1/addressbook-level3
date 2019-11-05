package io.xpire.model.item;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import io.xpire.commons.util.CollectionUtil;
import io.xpire.model.item.exceptions.DuplicateItemException;
import io.xpire.model.item.exceptions.ItemNotFoundException;
import io.xpire.model.item.sort.XpireMethodOfSorting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

//@@author febee99
/**
 * A list of items that enforces uniqueness between its elements and does not allow nulls.
 * An xpireItem is considered unique by comparing using {@code XpireItem#isSameItem(XpireItem)}. As such, adding and
 * updating of items uses XpireItem#isSameItem(XpireItem) for equality so as to ensure that the xpireItem being added
 * or updated is unique in terms of identity in the SortedUniqueXpireItemList. However, the removal of a xpireItem uses
 * XpireItem#equals(Object) so as to ensure that the xpireItem with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see XpireItem#isSameItem(Item)
 */
public class SortedUniqueXpireItemList implements Iterable<XpireItem> {
    private final ObservableList<XpireItem> internalList = FXCollections.observableArrayList();
    private XpireMethodOfSorting xpireMethodOfSorting = new XpireMethodOfSorting("name");
    private final SortedList<XpireItem> sortedInternalList = new SortedList<>(internalList,
            xpireMethodOfSorting.getComparator());
    private final ObservableList<XpireItem> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(this.sortedInternalList);


    /**
     * Returns true if the list contains an equivalent xpireItem as the given argument.
     */
    public boolean contains(XpireItem toCheck) {
        requireNonNull(toCheck);
        return this.internalList.stream().anyMatch(toCheck::isSameItem);
    }

    /**
     * Adds an xpireItem to the list.
     * The xpireItem must not already exist in the list.
     */
    public void add(XpireItem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        this.internalList.add(toAdd);
        xpireMethodOfSorting = new XpireMethodOfSorting("name");
    }

    /**
     * Retrieves method of sorting for Xpire.
     *
     * @return Xpire method of sorting.
     */
    public XpireMethodOfSorting getXpireMethodOfSorting() {
        return xpireMethodOfSorting;
    }

    /**
     * Replaces the xpireItem { @code target} in the list with {@code editedXpireItem}.
     * {@code target} must exist in the list.
     * The xpireItem identity of {@code editedXpireItem} must not be the same as another existing xpireItem in the list.
     */
    public void setItem(XpireItem target, XpireItem editedXpireItem) {
        CollectionUtil.requireAllNonNull(target, editedXpireItem);

        int index = this.internalList.indexOf(target);
        if (index == -1) {
            throw new ItemNotFoundException();
        }

        if (!target.isSameItem(editedXpireItem) && contains(editedXpireItem)) {
            throw new DuplicateItemException();
        }

        this.internalList.set(index, editedXpireItem);
    }

    /**
     * Removes the equivalent xpireItem from the list.
     * The xpireItem must exist in the list.
     */
    public void remove(XpireItem toRemove) {
        requireNonNull(toRemove);
        if (!this.internalList.remove(toRemove)) {
            throw new ItemNotFoundException();
        }
    }

    public void setItems(SortedUniqueXpireItemList replacement) {
        requireNonNull(replacement);
        this.internalList.setAll(replacement.sortedInternalList);
    }

    /**
     * Replaces the contents of this list with {@code xpireItems}.
     * {@code xpireItems} must not contain duplicate xpireItems.
     */
    public void setItems(List<XpireItem> xpireItems) {
        CollectionUtil.requireAllNonNull(xpireItems);
        if (!itemsAreUnique(xpireItems)) {
            throw new DuplicateItemException();
        }
        this.internalList.setAll(xpireItems);
    }

    /**
     * Set method of sorting.
     */
    public void setXpireMethodOfSorting(XpireMethodOfSorting method) {
        this.xpireMethodOfSorting = method;
        this.sortedInternalList.setComparator(this.xpireMethodOfSorting.getComparator());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<XpireItem> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
    }

    @Override
    public Iterator<XpireItem> iterator() {
        return this.sortedInternalList.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof SortedUniqueXpireItemList)) {
            return false;
        } else {
            SortedUniqueXpireItemList other = (SortedUniqueXpireItemList) obj;
            return this.internalUnmodifiableList.equals(other.internalUnmodifiableList);
        }
    }

    @Override
    public int hashCode() {
        return this.internalUnmodifiableList.hashCode();
    }

    /**
     * Returns true if {@code xpireItems} contains only unique xpireItems.
     */
    private boolean itemsAreUnique(List<XpireItem> xpireItems) {
        return xpireItems.size() == xpireItems.stream().distinct().count();
    }
}
