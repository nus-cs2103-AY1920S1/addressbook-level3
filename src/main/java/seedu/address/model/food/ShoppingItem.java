package seedu.address.model.food;

import java.util.Objects;

/**
 * Represents a shopping item.
 */
public class ShoppingItem extends Food{

    private final boolean bought;
    private final boolean urgent;

    public ShoppingItem(Name name, Amount amount) {
        super(name, amount);
        bought = false;
        urgent = false;
    }
    public ShoppingItem(Name name, Amount amount, boolean isBought, boolean isUrgent) {
        super(name, amount);
        bought = isBought;
        urgent = isUrgent;
    }

    public boolean isBought() {
        return this.bought;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    public ShoppingItem setBought(boolean bought) {
        return new ShoppingItem(this.getName(), this.getAmount(), bought, this.urgent);
    }

    public ShoppingItem setUrgent(boolean urgent) {
        return new ShoppingItem(this.getName(), this.getAmount(), this.bought, urgent);
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
