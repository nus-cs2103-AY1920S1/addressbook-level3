package dukecooks.model.mealplan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.model.mealplan.components.MealPlanNameContainsKeywordsPredicate;
import dukecooks.testutil.mealplan.MealPlanBuilder;

public class MealPlanNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MealPlanNameContainsKeywordsPredicate firstPredicate = new MealPlanNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        MealPlanNameContainsKeywordsPredicate secondPredicate = new MealPlanNameContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MealPlanNameContainsKeywordsPredicate firstPredicateCopy = new MealPlanNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different mealPlan -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        MealPlanNameContainsKeywordsPredicate predicate = new MealPlanNameContainsKeywordsPredicate(
                Collections.singletonList("Alice"));
        assertTrue(predicate.test(new MealPlanBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new MealPlanNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new MealPlanBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new MealPlanNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new MealPlanBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new MealPlanNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new MealPlanBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MealPlanNameContainsKeywordsPredicate predicate = new MealPlanNameContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new MealPlanBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new MealPlanNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new MealPlanBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new MealPlanNameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com",
                "Main", "Street"));
        assertFalse(predicate.test(new MealPlanBuilder().withName("Alice").build()));
    }
}
