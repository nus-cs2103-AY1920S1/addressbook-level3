package seedu.address.model.exercise.components;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Exercise}'s {@code MusclesTrained} matches any of the keywords given.
 */
public class MusclesTrainedContainsKeywordsPredicate implements Predicate<Exercise> {

    private final List<String> keywords;

    public MusclesTrainedContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Exercise exercise) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil
                        .containsWordIgnoreCase(exercise.getMusclesTrained()
                                .getPrimaryMuscle().getMuscleType(), keyword))
                || keywords.stream().anyMatch(keyword ->
                testSecondaryMuscles(exercise, keyword));
    }

    /**
     * Checks if the any of the secondary muscles in exercise contains the keyword
     * specified and returns true if it is present and false if otherwise.
     */
    private boolean testSecondaryMuscles(Exercise exercise, String keyword) {
        ArrayList<MuscleType> secondaryMuscles = exercise.getMusclesTrained().getSecondaryMuscles();
        for (MuscleType secondaryMuscle : secondaryMuscles) {
            boolean muscleContainsKeyword = StringUtil.containsWordIgnoreCase(secondaryMuscle
                     .getMuscleType(), keyword);
            if (muscleContainsKeyword) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MusclesTrainedContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((MusclesTrainedContainsKeywordsPredicate) other).keywords)); // state check
    }
}
