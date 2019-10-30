package io.xpire.model.item;

import static java.util.Objects.requireNonNull;

import io.xpire.commons.util.AppUtil;

/**
 * The list to be brought into view.
 */
public class ListToView {

    public static final String MESSAGE_CONSTRAINTS = "Only two lists (main and replenish) are available to view.";
    public static final String VIEW_MAIN = "main";
    public static final String VIEW_REPLENISH = "replenish";
    private final String list;

    /**
     * Constructs a {@code ListToView}.
     * @param list A valid list that can be viewed.
     */
    public ListToView(String list) {
        requireNonNull(list);
        AppUtil.checkArgument(ListToView.isValidListToView(list), MESSAGE_CONSTRAINTS);
        this.list = list;
    }

    public static boolean isValidListToView(String list) {
        return (list.equals(VIEW_MAIN) || list.equals(VIEW_REPLENISH));
    }

    /**
     * Returns the string value of the method of sorting.
     * @return The string representation of the method of sorting.
     */
    @Override
    public String toString() {
        return this.list;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ListToView)) {
            return false;
        } else {
            ListToView other = (ListToView) obj;
            return this.list.equals(other.list);
        }
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
