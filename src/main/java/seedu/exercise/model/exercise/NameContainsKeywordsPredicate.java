package seedu.exercise.model.exercise;

import java.util.List;
import java.util.function.Predicate;

import seedu.exercise.commons.util.StringUtil;

/**
 * Tests that a {@code Exercise}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Exercise> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Exercise exercise) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(exercise.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
