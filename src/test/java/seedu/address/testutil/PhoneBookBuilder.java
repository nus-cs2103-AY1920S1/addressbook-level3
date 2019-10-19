package seedu.address.testutil;

import seedu.address.model.DataBook;
import seedu.address.model.phone.Phone;

/**
 * A utility class to help with building {@code Phone} {@code DataBook}.
 * Example usage: <br>
 *     {@code DataBook<Phone> ab = new PhoneBookBuilder().withPhone("John", "Doe").build();}
 */
public class PhoneBookBuilder {

    private DataBook<Phone> phoneBook;

    public PhoneBookBuilder() {
        phoneBook = new DataBook<>();
    }

    public PhoneBookBuilder(DataBook<Phone> phoneBook) {
        this.phoneBook = phoneBook;
    }

    /**
     * Adds a new {@code Phone} to the {@code DataBook} that we are building.
     */
    public PhoneBookBuilder withPhone(Phone phone) {
        phoneBook.add(phone);
        return this;
    }

    public DataBook<Phone> build() {
        return phoneBook;
    }
}
