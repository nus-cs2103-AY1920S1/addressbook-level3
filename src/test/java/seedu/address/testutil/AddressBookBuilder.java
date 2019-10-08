package seedu.address.testutil;

import seedu.address.model.FinSec;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code FinSec ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private FinSec finSec;

    public AddressBookBuilder() {
        finSec = new FinSec();
    }

    public AddressBookBuilder(FinSec finSec) {
        this.finSec = finSec;
    }

    /**
     * Adds a new {@code FinSec} to the {@code FinSec} that we are building.
     */
    public AddressBookBuilder withPerson(seedu.address.model.contact.Contact contact) {
        finSec.addPerson(contact);
        return this;
    }

    public FinSec build() {
        return finSec;
    }
}
