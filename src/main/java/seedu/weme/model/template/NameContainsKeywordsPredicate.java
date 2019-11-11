package seedu.weme.model.template;

import java.util.List;
import java.util.function.Predicate;

import seedu.weme.commons.util.StringUtil;

/**
 * Tests that a {@code Template}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Template> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Template template) {
        return keywords.stream()
            .anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(template.getName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
