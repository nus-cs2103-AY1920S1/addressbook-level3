package seedu.address.testutil;

import seedu.address.model.FinSec;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code FinSec ab = new FinSecBuilder().withContact("John", "Doe").build();}
 */
public class FinSecBuilder {

    private FinSec finSec;

    public FinSecBuilder() {
        finSec = new FinSec();
    }

    public FinSecBuilder(FinSec finSec) {
        this.finSec = finSec;
    }

    /**
     * Adds a new {@code FinSec} to the {@code FinSec} that we are building.
     */
    public FinSecBuilder withContact(seedu.address.model.contact.Contact contact) {
        finSec.addContact(contact);
        return this;
    }

    public FinSec build() {
        return finSec;
    }
}
