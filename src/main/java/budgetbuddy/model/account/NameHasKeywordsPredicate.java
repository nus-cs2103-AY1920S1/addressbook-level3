package budgetbuddy.model.account;

import java.util.List;
import java.util.function.Predicate;

import budgetbuddy.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameHasKeywordsPredicate implements Predicate<Account> {
    private final List<String> keywords;

    public NameHasKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Account account) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(account.getName().name, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameHasKeywordsPredicate// instanceof handles nulls
                && keywords.equals(((NameHasKeywordsPredicate) other).keywords)); // state check
    }

}
