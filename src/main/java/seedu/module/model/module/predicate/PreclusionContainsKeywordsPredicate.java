package seedu.module.model.module.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.module.model.module.Module;

/**
 * Tests that a {@code Module}'s moduleCode partially or fully matches any of the keywords given.
 */
public class PreclusionContainsKeywordsPredicate implements Predicate<Module> {
    private final List<String> keywords;

    public PreclusionContainsKeywordsPredicate(List<String> keywords) {
        keywords.replaceAll(String::toLowerCase);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Module module) {
        if (!module.getPreclusion().isPresent()) {
            return false;
        }
        return keywords.stream()
                .anyMatch(keyword -> module.getPreclusion().get().toLowerCase().contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PreclusionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PreclusionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
