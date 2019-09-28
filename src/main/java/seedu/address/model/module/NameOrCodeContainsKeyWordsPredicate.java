package seedu.address.model.module;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Module}'s {@code Name} matches any of the keywords given.
 */
public class NameOrCodeContainsKeyWordsPredicate implements Predicate<Module> {
    private final List<String> keywords;

    public NameOrCodeContainsKeyWordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Module module) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(module.getName().fullName, keyword)) ||
                keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(module.getModuleCode().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameOrCodeContainsKeyWordsPredicate // instanceof handles nulls
                && keywords.equals(((NameOrCodeContainsKeyWordsPredicate) other).keywords)); // state check
    }

}
