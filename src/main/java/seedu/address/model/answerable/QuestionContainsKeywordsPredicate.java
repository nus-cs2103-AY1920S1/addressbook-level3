package seedu.address.model.answerable;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Answerable}'s {@code Question} matches any of the keywords given.
 */
public class QuestionContainsKeywordsPredicate implements Predicate<Answerable> {
    private final List<String> keywords;

    public QuestionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Answerable answerable) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(answerable.getQuestion().fullQuestion, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((QuestionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
