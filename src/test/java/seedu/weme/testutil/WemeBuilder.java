package seedu.weme.testutil;

import seedu.weme.model.Weme;
import seedu.weme.model.meme.Meme;

/**
 * A utility class to help with building Weme objects.
 * Example usage: <br>
 *     {@code Weme mb = new WemeBuilder().withMeme("John", "Doe").build();}
 */
public class WemeBuilder {

    private Weme weme;

    public WemeBuilder() {
        weme = new Weme();
    }

    public WemeBuilder(Weme weme) {
        this.weme = weme;
    }

    /**
     * Adds a new {@code Meme} to the {@code Weme} that we are building.
     */
    public WemeBuilder withMeme(Meme meme) {
        weme.addMeme(meme);
        return this;
    }

    public Weme build() {
        return weme;
    }
}
