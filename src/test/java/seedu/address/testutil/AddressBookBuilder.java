package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.food.Food;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withfood("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Food} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withfood(Food food) {
        addressBook.addFood(food);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
