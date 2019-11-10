package seedu.address.model.cap.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.common.Module;

/**
 * Tests that a {@code Module}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Module> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Module module) {
        boolean isModuleCodeMatch = keywords.stream()
            .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(module.getModuleCode().getModuleCode(),
                  keyword));
        boolean isTitleMatch = keywords.stream()
            .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(module.getTitle().getTitle(),
                  keyword));
        return isModuleCodeMatch || isTitleMatch;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
