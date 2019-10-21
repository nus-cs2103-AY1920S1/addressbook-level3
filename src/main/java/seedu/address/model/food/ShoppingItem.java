package seedu.address.model.food;

import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a shopping item.
 */
public class ShoppingItem extends Food {

    private final boolean bought;
    private final boolean urgent;
    //private final ExpiryDate expiryDate;

    public ShoppingItem(Name name, Amount amount) {
        super(name, amount);
        bought = false;
        urgent = false;
        //this.expiryDate = null;
    }

    public ShoppingItem(Name name, Amount amount, boolean isBought, boolean isUrgent) {
        super(name, amount);
        this.bought = isBought;
        this.urgent = isUrgent;
        //this.expiryDate = null;
    }
/*
    /**
     * Create a new Shopping item with expiry date when it is marked as bought.
     * @param name Name of shopping item to be added to grocery list
     * @param amount Amount of the item
     * @param expiryDate expiry date of the item when it is added to the grocery list
     */
    /*public ShoppingItem(Name name, Amount amount, ExpiryDate expiryDate) {
        super(name, amount);
        this.bought = true;
        this.urgent = false;
        //this.expiryDate = expiryDate;
    }
    */
    public boolean isBought() {
        return this.bought;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /*public ExpiryDate getExpiryDate() {
        return this.expiryDate;
    }*/

    public ShoppingItem setBought(boolean bought) {
        return new ShoppingItem(this.getName(), this.getAmount(), bought, this.isUrgent());
    }

    public ShoppingItem setUrgent(boolean urgent) {
        return new ShoppingItem(this.getName(), this.getAmount(), this.isBought(), urgent);
    }

    public GroceryItem getBoughtItem(Amount amount, ExpiryDate expiryDate) {
        return new GroceryItem(this.getName(), amount, expiryDate, new HashSet<>());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.getName(), this.getAmount(), this.isBought(), this.isUrgent());
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
