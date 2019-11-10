package seedu.deliverymans.model.restaurant;

import java.util.List;
import java.util.function.Predicate;

import seedu.deliverymans.commons.util.StringUtil;

/**
 * Tests that a {@code Restaurant}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Restaurant> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Restaurant restaurant) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(restaurant.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
