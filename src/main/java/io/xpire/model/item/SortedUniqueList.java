package io.xpire.model.item;

import java.util.List;

import io.xpire.model.item.sort.MethodOfSorting;
import javafx.collections.ObservableList;

/**
 *
 * @param <T>
 */
public interface SortedUniqueList<T extends Item> extends Iterable<T> {

    boolean contains(T toCheck);

    void add(T toAdd);

    void setItem(T targetItem, T editedItem);

    void remove(T toRemove);

    void setItems(SortedUniqueList<T> replacement);

    void setItems(List<T> items);

    void setMethodOfSorting(MethodOfSorting<T> method);

    ObservableList<T> asUnmodifiableObservableList();

}
