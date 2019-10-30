package seedu.address.model.question;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Question}'s {@code Subject} matches any of the keywords given.
 */
public class SubjectContainsKeywordsPredicate implements Predicate<Question> {
    private final List<String> keywords;

    public SubjectContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Question question) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(question.getSubject().subject, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SubjectContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SubjectContainsKeywordsPredicate) other).keywords)); // state check
    }
}
