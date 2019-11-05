package seedu.guilttrip.model.entry.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.tag.Tag;


/**
 * Tests that a {@code Entry's}'s {@code Tags} matches any of the keywords given.
 */
public class EntryContainsTagsPredicate implements Predicate<Entry> {

    private Set<Tag> tagListToCompare;

    public EntryContainsTagsPredicate(Set<Tag> tagListToCompare) {
        this.tagListToCompare = tagListToCompare;
    }

    @Override
    public boolean test(Entry entry) {
        return tagListToCompare.stream().allMatch(t -> entry.getTags().contains(t));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsTagsPredicate // instanceof handles nulls
                && tagListToCompare == (((EntryContainsTagsPredicate) other).tagListToCompare)); // state check
    }
}
