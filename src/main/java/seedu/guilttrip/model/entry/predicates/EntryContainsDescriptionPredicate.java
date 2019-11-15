package seedu.guilttrip.model.entry.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.guilttrip.commons.util.StringUtil;
import seedu.guilttrip.model.entry.Entry;

/**
 * Tests that a {@code Entry's}'s {@code Description} matches any of the keywords given.
 */
public class EntryContainsDescriptionPredicate implements Predicate<Entry> {
    private final List<String> keywords;

    public EntryContainsDescriptionPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Entry entry) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(entry.getDesc().fullDesc, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsDescriptionPredicate // instanceof handles nulls
                && keywords.equals(((EntryContainsDescriptionPredicate) other).keywords)); // state check
    }

}
