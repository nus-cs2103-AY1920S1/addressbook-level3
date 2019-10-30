package dukecooks.model.mealplan.components;

import java.util.List;
import java.util.function.Predicate;

import dukecooks.commons.util.StringUtil;

/**
 * Tests that a {@code MealPlan}'s {@code MealPlanName} matches any of the keywords given.
 */
public class MealPlanNameContainsKeywordsPredicate implements Predicate<MealPlan> {
    private final List<String> keywords;

    public MealPlanNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(MealPlan mealPlan) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(mealPlan.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MealPlanNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((MealPlanNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
