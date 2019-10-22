package seedu.weme.testutil;

import seedu.weme.model.MemeBook;

/**
 * A utility class for constructing typical {@code MemeBook} objects.
 */
public class TypicalMemeBook {

    private TypicalMemeBook() {
    } // prevents instantiation

    /**
     * Returns a {@code MemeBook} with all the typical memes.
     */
    public static MemeBook getTypicalMemeBook() {
        MemeBook mb = new MemeBook();
        mb.setMemes(TypicalMemes.getTypicalMemes());
        mb.setTemplates(TypicalTemplates.getTypicalTemplates());
        return mb;
    }

}
