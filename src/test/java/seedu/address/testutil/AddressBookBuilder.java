package seedu.address.testutil;

import seedu.address.model.Contact;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Contact ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Contact finSec;

    public AddressBookBuilder() {
        finSec = new Contact();
    }

    public AddressBookBuilder(Contact finSec) {
        this.finSec = finSec;
    }

    /**
     * Adds a new {@code Contact} to the {@code Contact} that we are building.
     */
    public AddressBookBuilder withPerson(seedu.address.model.contact.Contact contact) {
        finSec.addPerson(contact);
        return this;
    }

    public Contact build() {
        return finSec;
    }
}
