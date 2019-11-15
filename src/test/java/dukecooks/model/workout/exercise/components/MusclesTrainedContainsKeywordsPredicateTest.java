package dukecooks.model.workout.exercise.components;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.testutil.exercise.ExerciseBuilder;

public class MusclesTrainedContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MusclesTrainedContainsKeywordsPredicate firstPredicate =
                new MusclesTrainedContainsKeywordsPredicate(firstPredicateKeywordList);
        MusclesTrainedContainsKeywordsPredicate secondPredicate =
                new MusclesTrainedContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MusclesTrainedContainsKeywordsPredicate firstPredicateCopy =
                new MusclesTrainedContainsKeywordsPredicate(firstPredicateKeywordList);
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
        MusclesTrainedContainsKeywordsPredicate predicate = new MusclesTrainedContainsKeywordsPredicate(Collections
                .singletonList("Chest"));
        assertTrue(predicate.test(new ExerciseBuilder().withMusclesTrained("Chest").build()));

        // Multiple keywords
        predicate = new MusclesTrainedContainsKeywordsPredicate(Arrays.asList("Chest", "Back"));
        assertTrue(predicate.test(new ExerciseBuilder().withMusclesTrained("Chest Back").build()));

        // Only one matching keyword
        predicate = new MusclesTrainedContainsKeywordsPredicate(Arrays.asList("Chest", "Back"));
        assertTrue(predicate.test(new ExerciseBuilder().withMusclesTrained("Abs Chest").build()));

        // Mixed-case keywords
        predicate = new MusclesTrainedContainsKeywordsPredicate(Arrays.asList("ABs", "bacK"));
        assertTrue(predicate.test(new ExerciseBuilder().withMusclesTrained("Abs Chest").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MusclesTrainedContainsKeywordsPredicate predicate = new MusclesTrainedContainsKeywordsPredicate(Collections
                .emptyList());
        assertFalse(predicate.test(new ExerciseBuilder().withMusclesTrained("Abs").build()));

        // Non-matching keyword
        predicate = new MusclesTrainedContainsKeywordsPredicate(Arrays.asList("Chest"));
        assertFalse(predicate.test(new ExerciseBuilder().withMusclesTrained("Abs Back").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new MusclesTrainedContainsKeywordsPredicate(Arrays
                .asList("Bench", "Press", "medium"));
        assertFalse(predicate.test(new ExerciseBuilder().withMusclesTrained("Abs").build()));
    }
}
