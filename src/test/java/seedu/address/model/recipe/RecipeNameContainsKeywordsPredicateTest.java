package seedu.address.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RecipeBuilder;

public class RecipeNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RecipeNameContainsKeywordsPredicate firstPredicate = new RecipeNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        RecipeNameContainsKeywordsPredicate secondPredicate = new RecipeNameContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RecipeNameContainsKeywordsPredicate firstPredicateCopy = new RecipeNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different recipe -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        RecipeNameContainsKeywordsPredicate predicate = new RecipeNameContainsKeywordsPredicate(
                Collections.singletonList("Alice"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new RecipeNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new RecipeNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new RecipeNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RecipeNameContainsKeywordsPredicate predicate = new RecipeNameContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new RecipeBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new RecipeNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new RecipeNameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com",
                "Main", "Street"));
        assertFalse(predicate.test(new RecipeBuilder().withName("Alice").build()));
    }
}
