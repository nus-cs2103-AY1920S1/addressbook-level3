package seedu.ifridge.model.food;

import static seedu.ifridge.model.food.Amount.getUnit;
import static seedu.ifridge.model.food.Amount.getValue;
import static seedu.ifridge.model.food.Amount.hasSameAmountUnit;

import java.util.HashSet;
import java.util.Objects;

import javafx.collections.ObservableList;

/**
 * Represents a shopping item.
 */
public class ShoppingItem extends Food {

    private final boolean bought;
    private final boolean urgent;

    public ShoppingItem(Name name, Amount amount) {
        super(name, amount);
        bought = false;
        urgent = false;
    }

    public ShoppingItem(Name name, Amount amount, boolean isBought, boolean isUrgent) {
        super(name, amount);
        this.bought = isBought;
        this.urgent = isUrgent;
        //this.expiryDate = null;
    }
    public boolean isBought() {
        return this.bought;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

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

    /**
     * Checks if a ShoppingItem is bought in its entirety.
     * @param shoppingItem to check if completely bought
     * @param internalBoughtList boughtItems to compare shoppingItem with
     * @return true if shoppingItem is completely bought, false otherwise
     */
    public static boolean isCompletelyBought(ShoppingItem shoppingItem,
                                             ObservableList<GroceryItem> internalBoughtList) {
        if (!shoppingItem.isBought()) {
            return false;
        }
        Amount amountBought = shoppingItem.getAmountBought(internalBoughtList);
        return getValue(amountBought) >= getValue(shoppingItem.getAmount());
    }

    /**
     * Gives the amount of shopping item bought.
     * @param internalBoughtList boughtItems to compare shoppingItem with
     * @return Amount bought
     */
    public Amount getAmountBought(ObservableList<GroceryItem> internalBoughtList) {
        Amount shoppingAmount = this.getAmount();
        Amount totalBoughtAmount = new Amount("0" + getUnit(this.getAmount()));
        for (GroceryItem boughtItem : internalBoughtList) {
            if (!this.isSameName(boughtItem)) {
                continue;
            }
            Amount boughtAmount = boughtItem.getAmount();
            if (!hasSameAmountUnit(boughtAmount, shoppingAmount)) {
                boughtAmount = shoppingAmount.convertAmount(boughtAmount);
            }
            totalBoughtAmount = totalBoughtAmount.increaseBy(boughtAmount);
        }
        return totalBoughtAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ShoppingItem)) {
            return false;
        } else {
            return this.getName().equals(((ShoppingItem) o).getName())
                    && this.getAmount().equals(((ShoppingItem) o).getAmount())
                    && (this.isBought() == ((ShoppingItem) o).isBought())
                    && (this.isUrgent() == ((ShoppingItem) o).isUrgent());
        }
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
