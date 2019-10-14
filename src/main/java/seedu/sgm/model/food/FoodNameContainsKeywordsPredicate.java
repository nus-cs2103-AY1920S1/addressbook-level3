package seedu.sgm.model.food;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Food}'s {@code FoodName} matches any of the keywords given.
 */
public class FoodNameContainsKeywordsPredicate implements Predicate<Food> {
    private final List<String> keywords;

    public FoodNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Food food) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(food.getFoodName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FoodNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((FoodNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
