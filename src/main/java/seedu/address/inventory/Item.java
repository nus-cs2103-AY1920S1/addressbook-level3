package seedu.address.inventory;

import java.util.Objects;

/**
 * Represents a Item in the treasurerPro.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    private final String category;
    private final String description;
    private final int quantity;
    private final double cost;

    /**
     * Every field must be present and not null.
     */
    public Item(String description, String category, int quantity, double cost) {
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    /**
     * Returns true if both items of the same description have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two items.
     */
    public boolean isSameItem(Item otherItem) {
        if (otherItem == this) {
            return true;
        }

        return otherItem != null
                && otherItem.getDescription().equalsIgnoreCase(getDescription())
                && (otherItem.getCategory().equals(getCategory()) || otherItem.getCost() == (getCost()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) other;
        return otherItem.getDescription().equals(getDescription())
                && otherItem.getCategory().equals(getCategory())
                && otherItem.getQuantity() == getQuantity()
                && otherItem.getCost() == getCost();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, category, quantity, cost);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Description: ")
                .append(getCategory())
                .append(" Category: ")
                .append(getQuantity())
                .append(" Quantity: ")
                .append(getCost())
                .append(" Cost: ");
        return builder.toString();
    }

}

