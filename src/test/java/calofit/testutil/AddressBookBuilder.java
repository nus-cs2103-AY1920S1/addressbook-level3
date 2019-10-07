package calofit.testutil;

import calofit.model.AddressBook;
import calofit.model.meal.Meal;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
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
     * Adds a new {@code Meal} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withMeal(Meal meal) {
        addressBook.addMeal(meal);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
