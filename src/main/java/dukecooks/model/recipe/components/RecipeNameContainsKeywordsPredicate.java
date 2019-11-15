package dukecooks.model.recipe.components;

import java.util.List;
import java.util.function.Predicate;

import dukecooks.commons.util.StringUtil;

/**
 * Tests that a {@code Recipe}'s {@code RecipeNameName} matches any of the keywords given.
 */
public class RecipeNameContainsKeywordsPredicate implements Predicate<Recipe> {
    private final List<String> keywords;

    public RecipeNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recipe recipe) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(recipe.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RecipeNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
