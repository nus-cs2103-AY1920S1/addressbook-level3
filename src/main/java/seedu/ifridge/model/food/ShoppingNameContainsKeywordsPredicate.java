package seedu.ifridge.model.food;

import java.util.List;
import java.util.function.Predicate;

import seedu.ifridge.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class ShoppingNameContainsKeywordsPredicate implements Predicate<ShoppingItem> {
    private final List<String> keywords;

    public ShoppingNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ShoppingItem shoppingItem) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(shoppingItem.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShoppingNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ShoppingNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
