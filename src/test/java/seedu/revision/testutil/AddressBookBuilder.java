package seedu.revision.testutil;

import seedu.revision.model.AddressBook;
import seedu.revision.model.answerable.Answerable;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withAnswerable("John", "Doe").build();}
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
     * Adds a new {@code Answerable} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withAnswerable(Answerable answerable) {
        addressBook.addAnswerable(answerable);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
