package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 *  Tests that a {@code Note}'s {@code Title} matches any of the keywords given.
 */
public class FlashcardTitleContainsKeywordsPredicate implements Predicate<Flashcard> {
    private final List<String> keywords;

    public FlashcardTitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(flashcard.getTitle().fullTitle, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlashcardTitleContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((FlashcardTitleContainsKeywordsPredicate) other).keywords)); // state check
    }
}
