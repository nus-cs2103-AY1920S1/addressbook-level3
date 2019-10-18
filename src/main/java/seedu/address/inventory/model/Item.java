package seedu.address.inventory.model;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Represents a Item in the treasurerPro.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    private final String category;
    private final String description;
    private Integer quantity;
    private final Double cost;
    private Double totalCost;
    private Double price;
    private Double subtotal;
    private String id;

    /**
     * Every field must be present and not null.
     */
    public Item(String description, String category, Integer quantity, Double cost, Double price, int id) {
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.cost = Double.parseDouble(DECIMAL_FORMAT.format(cost));
        this.totalCost = Double.parseDouble(DECIMAL_FORMAT.format(quantity * cost));
        this.price = Double.parseDouble(DECIMAL_FORMAT.format(price));
        this.subtotal = Double.parseDouble(DECIMAL_FORMAT.format(quantity * price));
        this.id = "" + id;
    }

    /**
     * A separate constructor in the event that the Item is not for sale. Price can be set later.
     */
    public Item(String description, String category, int quantity, double cost, int i) {
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.cost = Double.parseDouble(DECIMAL_FORMAT.format(cost));
        this.totalCost = Double.parseDouble(DECIMAL_FORMAT.format(quantity * cost));
        this.price = 0.0;
        this.subtotal = 0.0;
        this.id = "" + i;
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

    public double getTotalCost() {
        return totalCost;
    }

    public double getPrice() {
        return price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateSubtotal();
    }

    public boolean getSellable() {
        if (price == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Updates the subtotal.
     */
    public void updateSubtotal() {
        this.subtotal = Double.parseDouble(DECIMAL_FORMAT.format(this.price * this.quantity));
    }

    public void updateTotalCost() {
        this.totalCost = Double.parseDouble(DECIMAL_FORMAT.format(this.cost * this.quantity));
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
        String msg = " | " + this.description + " | " + this.category
                + " | " + this.quantity + " | " + this.cost + " | " + this.price;
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
                && otherItem.getQuantity() == getQuantity()
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
                .append(getDescription() + "\n")
                .append(" Category: ")
                .append(getCategory() + "\n")
                .append(" Quantity: ")
                .append(getQuantity() + "\n")
                .append(" Cost: ")
                .append(getCost() + "\n")
                .append(" Total Cost: ")
                .append(getTotalCost() + "\n")
                .append(" Price: ")
                .append(getPrice() + "\n")
                .append(" Subtotal: ")
                .append(getSubtotal() + "\n");
        return builder.toString();
    }
}

