package seedu.sugarmummy.recmfood.predicates;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.sugarmummy.commons.util.StringUtil;
import seedu.sugarmummy.recmfood.model.Food;

/**
 * Tests that a {@code Food}'s {@code FoodName} matches any of the keywords given.
 */
public class FoodNameContainsKeywordsPredicate implements Predicate<Food> {
    private final List<String> keywords;

    public FoodNameContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Food food) {
        requireNonNull(food);

        //Shows the full food list if no keyword is specified.
        if (keywords.size() == 0) {
            return true;
        }
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
