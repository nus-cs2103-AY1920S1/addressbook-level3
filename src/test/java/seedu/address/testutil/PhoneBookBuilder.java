package seedu.address.testutil;

import seedu.address.model.PhoneBook;
import seedu.address.model.phone.Phone;

/**
 * A utility class to help with building PhoneBook objects.
 * Example usage: <br>
 *     {@code PhoneBook ab = new PhoneBookBuilder().withPhone("John", "Doe").build();}
 */
public class PhoneBookBuilder {

    private PhoneBook phoneBook;

    public PhoneBookBuilder() {
        phoneBook = new PhoneBook();
    }

    public PhoneBookBuilder(PhoneBook phoneBook) {
        this.phoneBook = phoneBook;
    }

    /**
     * Adds a new {@code Phone} to the {@code PhoneBook} that we are building.
     */
    public PhoneBookBuilder withPhone(Phone phone) {
        phoneBook.addPhone(phone);
        return this;
    }

    public PhoneBook build() {
        return phoneBook;
    }
}
