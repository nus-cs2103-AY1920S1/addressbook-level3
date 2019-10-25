package seedu.ifridge.testutil;

import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.food.GroceryItem;

/**
 * A utility class to help with building GroceryList objects.
 * Example usage: <br>
 *     {@code GroceryList ab = new GroceryListBuilder().withPerson("John", "Doe").build();}
 */
public class GroceryListBuilder {

    private GroceryList groceryList;

    public GroceryListBuilder() {
        groceryList = new GroceryList();
    }

    public GroceryListBuilder(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public GroceryListBuilder withPerson(GroceryItem food) {
        groceryList.addGroceryItem(food);
        return this;
    }

    public GroceryList build() {
        return groceryList;
    }
}
