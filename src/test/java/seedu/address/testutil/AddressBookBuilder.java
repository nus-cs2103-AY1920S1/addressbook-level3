package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.budget.Budget;
import seedu.address.model.spending.Spending;

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
     * Adds a new {@code Spending} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withSpending(Spending spending) {
        addressBook.addSpending(spending);
        return this;
    }

    /**
     * sets a new {@code Budget} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withBudget(Budget budget) {
        addressBook.setBudget(budget);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
