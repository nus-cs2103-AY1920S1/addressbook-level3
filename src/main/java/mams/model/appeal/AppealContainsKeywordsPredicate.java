package mams.model.appeal;

import java.util.List;
import java.util.function.Predicate;

import mams.commons.util.StringUtil;

/**
 * Tests that a {@code Module}'s {@code code} matches any of the keywords given.
 */
public class AppealContainsKeywordsPredicate implements Predicate<Appeal> {

    private final List<String> keywords;

    public AppealContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public boolean isEmpty() {
        return keywords.isEmpty();
    }

    public int getListSize() {
        return keywords.size();
    }

    @Override
    public boolean test(Appeal appeal) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(appeal.getAppealType(), keyword)
                        || StringUtil.containsWordIgnoreCase(appeal.getModule_to_add(), keyword)
                        || StringUtil.containsWordIgnoreCase(appeal.getStatus(), keyword)
                        || StringUtil.containsWordIgnoreCase(appeal.getAppealId(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppealContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AppealContainsKeywordsPredicate) other).keywords)); // state check
    }
}
