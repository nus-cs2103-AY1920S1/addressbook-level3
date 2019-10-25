package dukecooks.model.dashboard.components;

import java.util.List;
import java.util.function.Predicate;

import dukecooks.commons.util.StringUtil;

/**
 * Tests that a {@code Dashboard}'s {@code DashboardName} matches any of the keywords given.
 */
public class DashboardNameContainsKeywordsPredicate implements Predicate<Dashboard> {
    private final List<String> keywords;

    public DashboardNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Dashboard dashboard) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(dashboard.getDashboardName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DashboardNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DashboardNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
