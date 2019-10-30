package seedu.address.model.question;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Question}'s {@code Difficulty} matches any of the keywords given.
 */
public class DifficultyContainsKeywordsPredicate implements Predicate<Question> {
    private final List<String> keywords;

    public DifficultyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Question question) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(question.getDifficulty().difficulty, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DifficultyContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DifficultyContainsKeywordsPredicate) other).keywords)); // state check
    }
}
