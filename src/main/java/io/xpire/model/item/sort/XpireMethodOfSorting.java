package io.xpire.model.item.sort;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import io.xpire.commons.util.AppUtil;
import io.xpire.model.item.XpireItem;

//@@author febee99
/**
 * Represents a XpireMethodOfSorting in the expiry date tracker.
 * Guarantees: immutable
 */
public class XpireMethodOfSorting implements MethodOfSorting<XpireItem> {

    public static final String MESSAGE_CONSTRAINTS = "Sorting can only be done by 'name' or 'date'.";
    private final Comparator<XpireItem> nameSorter = Comparator.comparing(l->l.getName().toString(),
            String.CASE_INSENSITIVE_ORDER);
    private final Comparator<XpireItem> dateSorter = Comparator.comparing(l->l.getExpiryDate().getDate(),
            Comparator.nullsFirst(Comparator.naturalOrder()));
    private final Comparator<XpireItem> nameThenDateSorter = nameSorter.thenComparing(dateSorter);
    private final Comparator<XpireItem> dateThenNameSorter = dateSorter.thenComparing(nameSorter);
    private final String method;

    /**
     * Constructs a {@code XpireMethodOfSorting}.
     * @param method A valid method of sorting.
     */
    public XpireMethodOfSorting(String method) {
        requireNonNull(method);
        AppUtil.checkArgument(MethodOfSorting.isValidMethodOfSorting(method), MESSAGE_CONSTRAINTS);
        this.method = method;
    }


    /**
     * Returns a comparator for the given method of sorting.
     */
    public Comparator<XpireItem> getComparator() {
        switch (method) {
        case "date":
            return dateThenNameSorter;
        default:
            return nameThenDateSorter;
        }
    }

    /**
     * Returns the string value of the method of sorting.
     * @return The string representation of the method of sorting.
     */
    @Override
    public String toString() {
        return this.method;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof XpireMethodOfSorting)) {
            return false;
        } else {
            XpireMethodOfSorting other = (XpireMethodOfSorting) obj;
            return this.method.equals(other.method);
        }
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }
}
