package dukecooks.model.workout.exercise.components;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.testutil.exercise.ExerciseBuilder;

public class IntensityContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        IntensityContainsKeywordsPredicate firstPredicate =
                new IntensityContainsKeywordsPredicate(firstPredicateKeywordList);
        IntensityContainsKeywordsPredicate secondPredicate =
                new IntensityContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        IntensityContainsKeywordsPredicate firstPredicateCopy =
                new IntensityContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_intensityContainsKeywords_returnsTrue() {
        // One keyword
        IntensityContainsKeywordsPredicate predicate = new IntensityContainsKeywordsPredicate(Collections
                .singletonList("low"));
        assertTrue(predicate.test(new ExerciseBuilder().withIntensity(Intensity.LOW).build()));

        // Multiple keywords
        predicate = new IntensityContainsKeywordsPredicate(Arrays.asList("low", "high"));
        assertTrue(predicate.test(new ExerciseBuilder().withIntensity(Intensity.LOW).build()));
        assertTrue(predicate.test(new ExerciseBuilder().withIntensity(Intensity.HIGH).build()));

        // Mixed-case keywords
        predicate = new IntensityContainsKeywordsPredicate(Arrays.asList("lOw", "hIgH"));
        assertTrue(predicate.test(new ExerciseBuilder().withIntensity(Intensity.HIGH).build()));
        assertTrue(predicate.test(new ExerciseBuilder().withIntensity(Intensity.LOW).build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        IntensityContainsKeywordsPredicate predicate = new IntensityContainsKeywordsPredicate(Collections
                .emptyList());
        assertFalse(predicate.test(new ExerciseBuilder().withIntensity(Intensity.LOW).build()));

        // Non-matching keyword
        predicate = new IntensityContainsKeywordsPredicate(Arrays.asList("high"));
        assertFalse(predicate.test(new ExerciseBuilder().withIntensity(Intensity.LOW).build()));

        // Keywords match name, primary muscle, but does not match intensity
        predicate = new IntensityContainsKeywordsPredicate(Arrays
                .asList("Bench", "Press", "Chest"));
        assertFalse(predicate.test(new ExerciseBuilder().withIntensity(Intensity.LOW).build()));
    }
}
