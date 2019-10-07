package calofit.model.meal;

import calofit.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Meal}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Meal> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Meal meal) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(meal.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
