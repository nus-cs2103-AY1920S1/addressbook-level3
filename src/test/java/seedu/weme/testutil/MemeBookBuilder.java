package seedu.weme.testutil;

import seedu.weme.model.MemeBook;
import seedu.weme.model.meme.Meme;

/**
 * A utility class to help with building MemeBook objects.
 * Example usage: <br>
 *     {@code MemeBook mb = new MemeBookBuilder().withMeme("John", "Doe").build();}
 */
public class MemeBookBuilder {

    private MemeBook memeBook;

    public MemeBookBuilder() {
        memeBook = new MemeBook();
    }

    public MemeBookBuilder(MemeBook memeBook) {
        this.memeBook = memeBook;
    }

    /**
     * Adds a new {@code Meme} to the {@code MemeBook} that we are building.
     */
    public MemeBookBuilder withMeme(Meme meme) {
        memeBook.addMeme(meme);
        return this;
    }

    public MemeBook build() {
        return memeBook;
    }
}
