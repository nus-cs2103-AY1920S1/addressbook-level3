package seedu.address.testutil;

import seedu.address.model.GroceryList;
import seedu.address.model.food.GroceryItem;

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
        groceryList.addPerson(food);
        return this;
    }

    public GroceryList build() {
        return groceryList;
    }
}
