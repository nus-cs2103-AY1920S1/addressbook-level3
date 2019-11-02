package seedu.address.model.flashcard;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Note}'s {@code Title} matches any of the keyphrases given.
 */
public class FlashcardTitleMatchesKeywordPredicate implements Predicate<Flashcard> {
    private final String keyphrase;

    public FlashcardTitleMatchesKeywordPredicate(String keyphrase) {
        this.keyphrase = keyphrase;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return StringUtil.matchesPhraseIgnoreCase(flashcard.getTitle().fullTitle, keyphrase);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlashcardTitleMatchesKeywordPredicate // instanceof handles nulls
                && keyphrase.equals(((FlashcardTitleMatchesKeywordPredicate) other).keyphrase)); // state check
    }

}
