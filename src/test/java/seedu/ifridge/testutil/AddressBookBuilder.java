package seedu.ifridge.testutil;

import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.food.GroceryItem;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private GroceryList groceryList;

    public AddressBookBuilder() {
        groceryList = new GroceryList();
    }

    public AddressBookBuilder(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(GroceryItem food) {
        groceryList.addGroceryItem(food);
        return this;
    }

    public GroceryList build() {
        return groceryList;
    }
}
