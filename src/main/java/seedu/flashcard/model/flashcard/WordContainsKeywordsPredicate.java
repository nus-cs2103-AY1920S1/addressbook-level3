package seedu.flashcard.model.flashcard;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Flashcard}'s {@code Word} matches any of the keywords given.
 */
public class WordContainsKeywordsPredicate implements Predicate<Flashcard> {

    private final List<String> keywords;
    public WordContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return keywords.stream().
                anyMatch(keywords -> StringUtil.containsWordIgnoreCase(flashcard.getWord().word, keywords));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof WordContainsKeywordsPredicate
                && keywords.equals(((WordContainsKeywordsPredicate) other).keywords));
    }
}
