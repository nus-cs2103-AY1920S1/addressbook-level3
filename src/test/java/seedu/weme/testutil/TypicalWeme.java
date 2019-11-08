package seedu.weme.testutil;

import seedu.weme.model.Weme;

/**
 * A utility class for constructing typical {@code Weme} objects.
 */
public class TypicalWeme {

    private TypicalWeme() {
    } // prevents instantiation

    /**
     * Returns a {@code Weme} with all the typical memes and templates.
     */
    public static Weme getTypicalWeme() {
        Weme weme = new Weme();
        weme.setMemes(TypicalMemes.getTypicalMemes());
        weme.setTemplates(TypicalTemplates.getTypicalTemplates());
        weme.setStats(TypicalMemes.getTypicalStats());
        weme.setRecords(TypicalRecords.getTypicalRecords());
        return weme;
    }

}
