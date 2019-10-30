package io.xpire.model.item.sort;

import java.util.Comparator;

import io.xpire.model.item.Item;

/**
 *
 */
public class ReplenishMethodOfSorting implements MethodOfSorting<Item> {

    public static final String MESSAGE_CONSTRAINTS = "Sorting can only be done by 'name'.";
    public static final Comparator<Item> NAME_COMPARATOR = Comparator.comparing(l->l.getName().toString(),
            String.CASE_INSENSITIVE_ORDER);

    @Override
    public Comparator<Item> getComparator() {
        return NAME_COMPARATOR;
    }

}
