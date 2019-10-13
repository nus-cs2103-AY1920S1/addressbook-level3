package seedu.address.model.note;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Note}'s {@code Title} matches any of the keyphrases given.
 */
public class TitleMatchesKeywordsPredicate implements Predicate<Note> {
    private final String keyphrase;

    public TitleMatchesKeywordsPredicate(String keyphrase) {
        this.keyphrase = keyphrase;
    }

    @Override
    public boolean test(Note note) {
        return StringUtil.matchesPhraseIgnoreCase(note.getTitle().fullTitle, keyphrase);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TitleMatchesKeywordsPredicate // instanceof handles nulls
                && keyphrase.equals(((TitleMatchesKeywordsPredicate) other).keyphrase)); // state check
    }

}
