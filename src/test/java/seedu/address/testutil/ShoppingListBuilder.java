package seedu.address.testutil;

import seedu.address.model.ShoppingList;
import seedu.address.model.food.ShoppingItem;

/**
 * A Waste List Builder
 */
public class ShoppingListBuilder {

    private ShoppingList shoppingList;

    public ShoppingListBuilder() {
        shoppingList = new ShoppingList();
    }

    /**
     * Constructs a shopping list builder.
     * @param shoppingList the shopping list to construct a shopping list builder with.
     */
    public ShoppingListBuilder(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    /**
     * Adds an item to the shopping list
     * @param shoppingItem the item to be added
     * @return the waste list with the added item
     */
    public ShoppingListBuilder withShoppingItem(ShoppingItem shoppingItem) {
        shoppingList.addShoppingItem(shoppingItem);
        return this;
    }

    public ShoppingList build() {
        return shoppingList;
    }
}
