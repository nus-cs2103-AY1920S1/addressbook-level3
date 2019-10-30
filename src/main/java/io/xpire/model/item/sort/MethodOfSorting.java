package io.xpire.model.item.sort;

import java.util.Comparator;

import io.xpire.model.item.Item;

/**
 * The method of sorting.
 * @param <T> The class item to be sorted.
 */
public interface MethodOfSorting<T extends Item> {

    String SORT_NAME = "name";
    String SORT_DATE = "date";

    static boolean isValidMethodOfSorting(String test) {
        return (test.equals(SORT_NAME) || test.equals(SORT_DATE));
    }

    Comparator<T> getComparator();
}
