package seedu.module.model.module.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.module.model.module.Module;

/**
 * Tests that a {@code Module}'s description contains the keywords given.
 */
public class DescriptionContainsKeywordsPredicate implements Predicate<Module> {
    private final LevenshteinDistanceChecker checker = new LevenshteinDistanceChecker(2);
    private final List<String> keywords;

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        keywords.replaceAll(String::toLowerCase);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Module module) {
        return keywords.stream()
                .allMatch(keyword -> checker.fuzzyContains(module.getDescription().toLowerCase(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
