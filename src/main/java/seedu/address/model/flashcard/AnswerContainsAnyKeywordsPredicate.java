//@@author ShuTingY
package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code FlashCard}'s {@code Answer} matches any of the keywords given.
 */
public class AnswerContainsAnyKeywordsPredicate implements Predicate<FlashCard> {
    private final List<String> keywords;

    public AnswerContainsAnyKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test (FlashCard flashcard) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(flashcard.getAnswer().fullAnswer, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AnswerContainsAnyKeywordsPredicate
                && keywords.equals(((AnswerContainsAnyKeywordsPredicate) other).keywords));
    }
}
