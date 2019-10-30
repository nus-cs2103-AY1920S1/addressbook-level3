package seedu.address.model.person.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.Entry;
import seedu.address.model.tag.Tag;


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
        return tagListToCompare.equals(entry.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsTagsPredicate // instanceof handles nulls
                && tagListToCompare == (((EntryContainsTagsPredicate) other).tagListToCompare)); // state check
    }
}
