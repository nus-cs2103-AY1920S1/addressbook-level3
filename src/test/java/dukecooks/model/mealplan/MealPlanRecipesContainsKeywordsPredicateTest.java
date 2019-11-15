package dukecooks.model.mealplan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.model.mealplan.components.MealPlanRecipesContainsKeywordsPredicate;
import dukecooks.testutil.mealplan.MealPlanBuilder;

public class MealPlanRecipesContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MealPlanRecipesContainsKeywordsPredicate firstPredicate = new MealPlanRecipesContainsKeywordsPredicate(
                firstPredicateKeywordList);
        MealPlanRecipesContainsKeywordsPredicate secondPredicate = new MealPlanRecipesContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MealPlanRecipesContainsKeywordsPredicate firstPredicateCopy = new MealPlanRecipesContainsKeywordsPredicate(
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
        MealPlanRecipesContainsKeywordsPredicate predicate = new MealPlanRecipesContainsKeywordsPredicate(
                Collections.singletonList("Alice Bob"));
        assertTrue(predicate.test(new MealPlanBuilder().withDay1("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay2("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay3("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay4("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay5("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay6("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay7("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new MealPlanRecipesContainsKeywordsPredicate(Arrays.asList("aLIce bOB"));
        assertTrue(predicate.test(new MealPlanBuilder().withDay1("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay2("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay3("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay4("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay5("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay6("Alice Bob").build()));
        assertTrue(predicate.test(new MealPlanBuilder().withDay7("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MealPlanRecipesContainsKeywordsPredicate predicate = new MealPlanRecipesContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new MealPlanBuilder().withDay1("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay2("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay3("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay4("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay5("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay6("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay7("Alice").build()));

        // Non-matching keyword
        predicate = new MealPlanRecipesContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new MealPlanBuilder().withDay1("Alice Bob").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay2("Alice Bob").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay3("Alice Bob").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay4("Alice Bob").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay5("Alice Bob").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay6("Alice Bob").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay7("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new MealPlanRecipesContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com",
                "Main", "Street"));
        assertFalse(predicate.test(new MealPlanBuilder().withDay1("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay2("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay3("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay4("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay5("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay6("Alice").build()));
        assertFalse(predicate.test(new MealPlanBuilder().withDay7("Alice").build()));
    }
}
