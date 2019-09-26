package seedu.mark.testutil;

import seedu.mark.model.AddressBook;
import seedu.mark.model.bookmark.Bookmark;

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
     * Adds a new {@code Bookmark} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Bookmark bookmark) {
        addressBook.addPerson(bookmark);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
