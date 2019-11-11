package seedu.module.model.module.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.module.model.module.Module;

/**
 * Tests that a {@code Module}'s moduleCode partially or fully matches any of the keywords given.
 */

public class SemesterContainsKeywordsPredicate implements Predicate<Module> {
    private final List<Integer> keywords;

    public SemesterContainsKeywordsPredicate(List<Integer> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Module module) {
        return keywords.stream()
                .anyMatch(keyword -> module.getListOfOfferedSemesters().stream()
                        .anyMatch(x -> x.equals(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SemesterContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SemesterContainsKeywordsPredicate) other).keywords)); // state check
    }

}
