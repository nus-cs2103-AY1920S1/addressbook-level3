package io.xpire.model;

import io.xpire.model.item.Item;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a list.
 */
public interface ReadOnlyListView<T extends Item> {
    /**
     * Returns an unmodifiable view of the item list.
     * This list will not contain any duplicate items.
     */
    ObservableList<T> getItemList();

    /**
     * Returns true if the two objects are equal.
     */
    @Override
    boolean equals(Object obj);
}
