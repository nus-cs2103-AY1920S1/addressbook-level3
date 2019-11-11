package dukecooks.model.mealplan.components;

import java.util.List;
import java.util.function.Predicate;

import dukecooks.commons.util.StringUtil;

/**
 * Tests that a {@code MealPlan}'s recipe's {@code RecipeName} matches any of the keywords given.
 */
public class MealPlanRecipesContainsKeywordsPredicate implements Predicate<MealPlan> {
    private final List<String> keywords;

    public MealPlanRecipesContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(MealPlan mealPlan) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCaseIgnoreSpace(mealPlan.getRecipes(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MealPlanRecipesContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((MealPlanRecipesContainsKeywordsPredicate) other).keywords)); // state check
    }

}
