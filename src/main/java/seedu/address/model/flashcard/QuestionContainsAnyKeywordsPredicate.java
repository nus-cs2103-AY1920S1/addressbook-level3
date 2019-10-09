package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code FlashCard}'s {@code Question} matches any of the keywords given.
 */
public class QuestionContainsAnyKeywordsPredicate implements Predicate<FlashCard> {
    private final List<String> keywords;

    public QuestionContainsAnyKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(FlashCard flashCard) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(flashCard.getQuestion().fullQuestion, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionContainsAnyKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((QuestionContainsAnyKeywordsPredicate) other).keywords)); // state check
    }

}
