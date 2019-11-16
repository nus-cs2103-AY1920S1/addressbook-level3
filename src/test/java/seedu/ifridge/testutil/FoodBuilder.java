package seedu.ifridge.testutil;

import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Food;
import seedu.ifridge.model.food.Name;

/**
 * A utility class to help with building Food objects.
 */
public class FoodBuilder {
    public static final String DEFAULT_NAME = "Yakult";
    public static final String DEFAULT_AMOUNT = "5units";

    private Name name;
    private Amount amount;

    public FoodBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
    }

    /**
     * Initializes the FoodBuilder with the data of {@code foodToCopy}.
     */
    public FoodBuilder(Food foodToCopy) {
        name = foodToCopy.getName();
        amount = foodToCopy.getAmount();
    }

    /**
     * Sets the {@code Name} of the {@code Food} that we are building.
     */
    public FoodBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Food} that we are building.
     */
    public FoodBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    public Food build() {
        return new Food(name, amount);
    }

}
