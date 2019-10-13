package seedu.address.testutil;

import seedu.address.model.Catalog;
import seedu.address.model.book.Book;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
// TODO EDIT THIS
public class CatalogBuilder {

    private Catalog catalog;

    public CatalogBuilder() {
        catalog = new Catalog();
    }

    public CatalogBuilder(Catalog catalog) {
        this.catalog = catalog;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public CatalogBuilder withPerson(Book book) {
        catalog.addBook(book);
        return this;
    }

    public Catalog build() {
        return catalog;
    }
}
