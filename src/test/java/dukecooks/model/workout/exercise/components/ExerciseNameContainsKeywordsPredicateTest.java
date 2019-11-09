package dukecooks.model.workout.exercise.components;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.testutil.exercise.ExerciseBuilder;

public class ExerciseNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ExerciseNameContainsKeywordsPredicate firstPredicate =
                new ExerciseNameContainsKeywordsPredicate(firstPredicateKeywordList);
        ExerciseNameContainsKeywordsPredicate secondPredicate =
                new ExerciseNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ExerciseNameContainsKeywordsPredicate firstPredicateCopy =
                new ExerciseNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ExerciseNameContainsKeywordsPredicate predicate = new ExerciseNameContainsKeywordsPredicate(Collections
                .singletonList("Alice"));
        assertTrue(predicate.test(new ExerciseBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new ExerciseNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ExerciseBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ExerciseNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new ExerciseBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ExerciseNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ExerciseBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ExerciseNameContainsKeywordsPredicate predicate = new ExerciseNameContainsKeywordsPredicate(Collections
                .emptyList());
        assertFalse(predicate.test(new ExerciseBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ExerciseNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ExerciseBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ExerciseNameContainsKeywordsPredicate(Arrays
                .asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new ExerciseBuilder().withName("Alice").build()));
    }
}
