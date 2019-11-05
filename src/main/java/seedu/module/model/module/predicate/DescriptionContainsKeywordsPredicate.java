package seedu.module.model.module.predicate;

import java.util.function.Predicate;

import seedu.module.model.module.Module;

/**
 * Tests that a {@code Module}'s description contains the keywords given.
 */
public class DescriptionContainsKeywordsPredicate implements Predicate<Module> {
    private final LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(2);
    private final String keyword;

    public DescriptionContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword.toLowerCase().trim();
    }

    @Override
    public boolean test(Module module) {
        return checker.fuzzyContains(module.getDescription().toLowerCase(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((DescriptionContainsKeywordsPredicate) other).keyword)); // state check
    }

}
