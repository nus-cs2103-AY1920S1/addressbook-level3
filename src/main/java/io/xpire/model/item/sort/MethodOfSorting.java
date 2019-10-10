package io.xpire.model.item.sort;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import io.xpire.commons.util.AppUtil;
import io.xpire.model.item.Item;

/**
 * Represents a MethodOfSorting in the expiry date tracker.
 * Guarantees: immutable; name is valid as declared in {@link #isValidMethodOfSorting(String)}
 */
public class MethodOfSorting {

    public static final String MESSAGE_CONSTRAINTS = "Sorting can only be done by 'name' or 'date'.";
    private final String method;

    /**
     * Constructs a {@code MethodOfSorting}.
     * @param method A valid method of sorting.
     */
    public MethodOfSorting(String method) {
        requireNonNull(method);
        AppUtil.checkArgument(isValidMethodOfSorting(method), MESSAGE_CONSTRAINTS);
        this.method = method;
    }

    /**
     * Returns true if a given string is a valid method of sorting.
     */
    public static boolean isValidMethodOfSorting(String test) {
        return (test.equals("name") || test.equals("date"));
    }

    /**
     * Returns a comparator for the given method of sorting.
     */
    public Comparator<Item> getComparator() {
        final Comparator<Item> nameSorter = Comparator.comparing(l->l.getName().toString(),
                String.CASE_INSENSITIVE_ORDER);
        final Comparator<Item> dateSorter = Comparator.comparing(l->l.getExpiryDate().getDate(),
                Comparator.nullsFirst(Comparator.naturalOrder()));
        final Comparator<Item>nameThenDateSorter = nameSorter.thenComparing(dateSorter);
        switch (method) {
        case "date":
            return dateSorter;
        default:
            return nameThenDateSorter;
        }
    }

    /**
     * Returns the string value of the method of sorting.
     * @return The string representation of the method of sorting.
     */
    public String getValue() {
        return this.method;
    }


}
