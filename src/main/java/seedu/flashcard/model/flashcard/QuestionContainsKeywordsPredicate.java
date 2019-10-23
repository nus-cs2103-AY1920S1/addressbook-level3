package seedu.flashcard.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Flashcard}'s {@code Question} matches any of the keywords given.
 */
public class QuestionContainsKeywordsPredicate implements Predicate<Flashcard> {

    private final List<String> keywords;
    public QuestionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return keywords.stream()
                .anyMatch(keywords -> StringUtil.containsWordIgnoreCase(flashcard.getQuestion().question, keywords));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof QuestionContainsKeywordsPredicate
                && keywords.equals(((QuestionContainsKeywordsPredicate) other).keywords));
    }
}
