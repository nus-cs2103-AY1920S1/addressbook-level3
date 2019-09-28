package seedu.address.testutil;

import seedu.address.model.MemeBook;
import seedu.address.model.meme.Meme;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code MemeBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private MemeBook memeBook;

    public AddressBookBuilder() {
        memeBook = new MemeBook();
    }

    public AddressBookBuilder(MemeBook memeBook) {
        this.memeBook = memeBook;
    }

    /**
     * Adds a new {@code Meme} to the {@code MemeBook} that we are building.
     */
    public AddressBookBuilder withMeme(Meme meme) {
        memeBook.addMeme(meme);
        return this;
    }

    public MemeBook build() {
        return memeBook;
    }
}
