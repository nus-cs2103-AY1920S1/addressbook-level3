package seedu.address.model.exercise.components;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Exercise}'s {@code Intensity} matches any of the keywords given.
 */
public class IntensityContainsKeywordsPredicate implements Predicate<Exercise> {
    private final List<String> keywords;

    public IntensityContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Exercise exercise) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil
                        .containsWordIgnoreCase(exercise.getIntensity().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IntensityContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((IntensityContainsKeywordsPredicate) other).keywords)); // state check
    }

}
