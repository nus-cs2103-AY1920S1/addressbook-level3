package dukecooks.model.workout.exercise.components;

import java.util.List;
import java.util.function.Predicate;

import dukecooks.commons.util.StringUtil;

/**
 * Tests that a {@code Exercise}'s {@code Name} matches any of the keywords given.
 */
public class ExerciseNameContainsKeywordsPredicate implements Predicate<Exercise> {
    private final List<String> keywords;

    public ExerciseNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Exercise exercise) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil
                        .containsWordIgnoreCase(exercise.getExerciseName().exerciseName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExerciseNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ExerciseNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
