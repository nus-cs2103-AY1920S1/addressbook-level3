package mams.model.module;

import java.util.List;
import java.util.function.Predicate;

import mams.commons.util.StringUtil;

/**
 * Tests that a {@code Module}'s {@code code} matches any of the keywords given.
 */
public class ModuleContainsKeywordsPredicate implements Predicate<Module> {

    private final List<String> keywords;

    public ModuleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public boolean isEmpty() {
        return keywords.isEmpty();
    }

    public int getListSize() {
        return keywords.size();
    }

    @Override
    public boolean test(Module module) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(module.getModuleCode(), keyword)
                        || StringUtil.containsWordIgnoreCase(module.getModuleName(), keyword)
                        || StringUtil.containsWordIgnoreCase(module.getModuleDescription(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ModuleContainsKeywordsPredicate) other).keywords)); // state check
    }
}
