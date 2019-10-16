package seedu.address.model.food;

import java.util.Objects;

/**
 * Represents a shopping item.
 */
public class ShoppingItem extends Food {

    private final boolean bought;
    private final boolean urgent;
    private final ExpiryDate expiryDate;

    //empty constructor to be removed after Model integration
    public ShoppingItem() {
        super(new Name("ALICE"), new Amount("21"));
        this.bought = false;
        this.urgent = false;
        this.expiryDate = null;
    }

    public ShoppingItem(Name name, Amount amount) {
        super(name, amount);
        bought = false;
        urgent = false;
        this.expiryDate = null;
    }

    public ShoppingItem(Name name, Amount amount, boolean isUrgent) {
        super(name, amount);
        this.bought = false;
        this.urgent = isUrgent;
        this.expiryDate = null;
    }

    /**
     * Create a new Shopping item with expiry date when it is marked as bought.
     * @param name Name of shopping item to be added to grocery list
     * @param amount Amount of the item
     * @param expiryDate expiry date of the item when it is added to the grocery list
     */
    public ShoppingItem(Name name, Amount amount, ExpiryDate expiryDate) {
        super(name, amount);
        this.bought = true;
        this.urgent = false;
        this.expiryDate = expiryDate;
    }

    public boolean isBought() {
        return this.bought;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    public ExpiryDate getExpiryDate() {
        return this.expiryDate;
    }

    public ShoppingItem setBought(ExpiryDate expiryDate) {
        return new ShoppingItem(this.getName(), this.getAmount(), expiryDate);
    }

    public ShoppingItem setUrgent(boolean urgent) {
        return new ShoppingItem(this.getName(), this.getAmount(), urgent);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.getName(), this.getAmount(), this.isBought(), this.isUrgent(), this.expiryDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" ")
                .append(getAmount())
                .append(" isBought: ")
                .append(this.bought)
                .append(" isUrgent: ")
                .append(this.urgent);
        return builder.toString();
    }
}
