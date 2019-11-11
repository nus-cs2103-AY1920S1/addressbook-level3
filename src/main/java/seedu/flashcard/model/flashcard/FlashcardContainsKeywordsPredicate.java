package seedu.flashcard.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import seedu.flashcard.commons.util.StringUtil;

/**
 * Tests that a {@code Flashcard}'s {@code Question}, {@code Definition}, {@code Answer}
 * matches any of the keywords given.
 */
public class FlashcardContainsKeywordsPredicate implements Predicate<Flashcard> {

    private final List<String> keywords;
    public FlashcardContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return keywords.stream()
                .anyMatch(keywords -> StringUtil.containsWordIgnoreCase(flashcard.getQuestion().question, keywords))
            || keywords.stream()
                .anyMatch(keywords -> StringUtil.containsWordIgnoreCase(flashcard.getDefinition().definition, keywords))
            || keywords.stream()
                .anyMatch(keywords -> StringUtil.containsWordIgnoreCase(flashcard.getAnswer().answer, keywords));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FlashcardContainsKeywordsPredicate
                && keywords.equals(((FlashcardContainsKeywordsPredicate) other).keywords));
    }
}
