package seedu.moneygowhere.logic.sorting;

import java.util.Objects;

/**
 * Fields for sorting based on an attribute and order.
 */
public class SortField {
    private final SortAttribute attribute;
    private final SortOrder order;

    /**
     * Sorts a given field with an order.
     * @param attribute Attribute being sorted
     * @param order Ascending or descending
     */
    public SortField(SortAttribute attribute, SortOrder order) {
        this.attribute = attribute;
        this.order = order;
    }

    /**
     * Gets an attribute.
     * @return Sort Attribute
     */
    public SortAttribute getAttribute() {
        return attribute;
    }

    /**
     * Gets the sort order.
     * @return Sort Order
     */
    public SortOrder getOrder() {
        return order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, order);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortField // instanceof handles nulls
                && (attribute.equals(((SortField) other).attribute))
                && (order.equals(((SortField) other).order)));
    }

    @Override
    public String toString() {
        return getAttribute() + " (" + getOrder() + ")";
    }
}
