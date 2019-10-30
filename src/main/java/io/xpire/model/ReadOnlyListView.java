package io.xpire.model;

import io.xpire.model.item.Item;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an xpire.
 */
public interface ReadOnlyListView<T extends Item> {
    /**
     * Returns an unmodifiable view of the xpireItem list.
     * This list will not contain any duplicate items.
     */
    ObservableList<T> getItemList();
}
