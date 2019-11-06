package seedu.revision.testutil;

import seedu.revision.model.RevisionTool;
import seedu.revision.model.answerable.Answerable;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code RevisionTool ab = new AddressBookBuilder().withAnswerable("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private RevisionTool addressBook;

    public AddressBookBuilder() {
        addressBook = new RevisionTool();
    }

    public AddressBookBuilder(RevisionTool addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Answerable} to the {@code RevisionTool} that we are building.
     */
    public AddressBookBuilder withAnswerable(Answerable answerable) {
        addressBook.addAnswerable(answerable);
        return this;
    }

    public RevisionTool build() {
        return addressBook;
    }
}
