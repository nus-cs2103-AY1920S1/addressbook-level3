package seedu.moneygowhere.model.spending;

import java.util.List;
import java.util.function.Predicate;

import seedu.moneygowhere.commons.util.StringUtil;

/**
 * Tests that a {@code Spending}'s {@code Remark} matches any of the keywords given.
 */
public class RemarkContainsKeywordsPredicate implements Predicate<Spending> {
    private final List<String> keywords;

    public RemarkContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Spending spending) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(spending.getRemark().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RemarkContainsKeywordsPredicate) other).keywords)); // state check
    }

}
