package seedu.address.inventory.model;

import java.util.Objects;

/**
 * Represents a Item in the treasurerPro.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    private final String category;
    private final String description;
    private int quantity;
    private final double cost;
    private static double price;
    private static String id;

    /**
     * Every field must be present and not null.
     */
    public Item(String description, String category, int quantity, double cost, double price, int id) {
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.cost = cost;
        this.price = price;
        this.id = "" + id;
    }

    /**
     * A separate constructor in the event that the Item is not for sale. Price can be set later.
     */
    public Item(String description, String category, int quantity, double cost) {
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.cost = cost;
        this.price = 0;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = "" + i;
    }

    /**
     * Stores the attributes of the Item into a String, for storage in a File.
     * @return a String containing the attributes of the Item.
     */
    public String toWriteIntoFile() {
        String msg = this.description + " | " + this.category +
                " | " + this.quantity + " | " + this.cost + " | " + this.price;
        return msg;
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
                && (otherItem.getCategory().equals(getCategory()) || otherItem.getCost() == (getCost())
                    || otherItem.getPrice() == getPrice());
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
                //&& otherItem.getQuantity() == getQuantity()
                && otherItem.getCost() == getCost()
                && otherItem.getPrice() == getPrice();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, category, quantity, cost, price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Description: ")
                .append(getDescription())
                .append(" Category: ")
                .append(getCategory())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Cost: ")
                .append(getCost())
                .append(" Price: ")
                .append(getPrice());
        return builder.toString();
    }
}

