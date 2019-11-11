package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code FlashCard}'s {@code Question/Answer} matches any of the keywords given.
 */
public class QuestionOrAnswerContainsAnyKeywordsPredicate implements Predicate<FlashCard> {
    private final List<String> keywords;

    public QuestionOrAnswerContainsAnyKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(FlashCard flashCard) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(flashCard.getQuestion().fullQuestion, keyword)
                || StringUtil.containsWordIgnoreCase(flashCard.getAnswer().fullAnswer, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionOrAnswerContainsAnyKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((QuestionOrAnswerContainsAnyKeywordsPredicate) other).keywords)); // state check
    }

}
