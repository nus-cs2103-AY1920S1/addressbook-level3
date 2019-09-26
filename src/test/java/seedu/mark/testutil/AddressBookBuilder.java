package seedu.mark.testutil;

import seedu.mark.model.BookmarkManager;
import seedu.mark.model.bookmark.Bookmark;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code BookmarkManager ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private BookmarkManager addressBook;

    public AddressBookBuilder() {
        addressBook = new BookmarkManager();
    }

    public AddressBookBuilder(BookmarkManager addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Bookmark} to the {@code BookmarkManager} that we are building.
     */
    public AddressBookBuilder withPerson(Bookmark bookmark) {
        addressBook.addBookmark(bookmark);
        return this;
    }

    public BookmarkManager build() {
        return addressBook;
    }
}
