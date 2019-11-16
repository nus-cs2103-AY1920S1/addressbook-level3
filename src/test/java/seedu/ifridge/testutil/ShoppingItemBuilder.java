package seedu.ifridge.testutil;

import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * A utility class to help with building Person objects.
 */
public class ShoppingItemBuilder {
    public static final String DEFAULT_NAME = "Orange";
    public static final String DEFAULT_AMOUNT = "300g";
    public static final boolean DEFAULT_BOUGHT = false;
    public static final boolean DEFAULT_URGENT = false;

    private Name name;
    private Amount amount;
    private boolean bought;
    private boolean urgent;

    public ShoppingItemBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        bought = DEFAULT_BOUGHT;
        urgent = DEFAULT_URGENT;
    }

    /**
     * Initializes the ShoppingItemBuilder with the data of {@code shoppingItemToCopy}.
     */
    public ShoppingItemBuilder(ShoppingItem shoppingItemToCopy) {
        name = shoppingItemToCopy.getName();
        amount = shoppingItemToCopy.getAmount();
    }

    /**
     * Sets the {@code Name} of the {@code ShoppingList} that we are building.
     */
    public ShoppingItemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code ShoppingItem} that we are building.
     */
    public ShoppingItemBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code bought} of the {@code ShoppingItem} that we are building.
     * @param bought boolean value to set as bought status
     * @return {@code ShoppingItemBuilder} with new bought status
     */
    public ShoppingItemBuilder withBought(boolean bought) {
        this.bought = bought;
        return this;
    }

    /**
     * Sets the {@code urgent} of the {@code ShoppingItem} that we are building.
     * @param urgent boolean value to set as urgent status
     * @return {@code ShoppingItemBuilder} with new urgent status
     */
    public ShoppingItemBuilder withUrgent(boolean urgent) {
        this.urgent = urgent;
        return this;
    }

    public ShoppingItem build() {
        return new ShoppingItem(name, amount, bought, urgent);
    }

}
