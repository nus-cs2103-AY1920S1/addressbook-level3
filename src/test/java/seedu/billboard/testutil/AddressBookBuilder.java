package seedu.billboard.testutil;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.person.Expense;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Billboard ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Billboard addressBook;

    public AddressBookBuilder() {
        addressBook = new Billboard();
    }

    public AddressBookBuilder(Billboard addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Expense} to the {@code Billboard} that we are building.
     */
    public AddressBookBuilder withPerson(Expense expense) {
        addressBook.addExpense(expense);
        return this;
    }

    public Billboard build() {
        return addressBook;
    }
}
