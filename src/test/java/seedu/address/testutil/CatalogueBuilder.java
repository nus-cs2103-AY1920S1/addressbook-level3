package seedu.address.testutil;

import seedu.address.model.Catalogue;
import seedu.address.model.book.Book;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class CatalogueBuilder {

    private Catalogue catalogue;

    public CatalogueBuilder() {
        catalogue = new Catalogue();
    }

    public CatalogueBuilder(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public CatalogueBuilder withPerson(Book book) {
        catalogue.addBook(book);
        return this;
    }

    public Catalogue build() {
        return catalogue;
    }
}
