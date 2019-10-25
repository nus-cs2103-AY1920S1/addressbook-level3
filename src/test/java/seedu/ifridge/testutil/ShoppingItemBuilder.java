package seedu.ifridge.testutil;

import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * A utility class to help with building Person objects.
 */
public class ShoppingItemBuilder {
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_AMOUNT = "300g";
    //public static final String DEFAULT_EXPIRY_DATE = "10.08.2019";

    private Name name;
    private Amount amount;
    //private Set<Tag> tags;

    public ShoppingItemBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public ShoppingItemBuilder(ShoppingItem shoppingItemToCopy) {
        name = shoppingItemToCopy.getName();
        amount = shoppingItemToCopy.getAmount();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ShoppingItemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ShoppingItemBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /*
    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    /*public GroceryItemBuilder withExpiryDate(String expiryDate) {
        this.expiryDate = new ExpiryDate(expiryDate);
        return this;
    }*/

    /*/**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    /*public GroceryItemBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }*/

    public ShoppingItem build() {
        return new ShoppingItem(name, amount);
    }

}
