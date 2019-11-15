package seedu.guilttrip.model.entry.predicates;

import java.util.function.Predicate;

import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Entry;

/**
 * Tests that a {@code Entry}'s {@code Category} is larger than the given category.
 */
public class EntryContainsCategoryPredicate implements Predicate<Entry> {
    private final Category category;

    public EntryContainsCategoryPredicate(Category category) {
        this.category = category;
    }

    @Override
    public boolean test(Entry entry) {
        return category.equals(entry.getCategory());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsCategoryPredicate // instanceof handles nulls
                && category.equals(((EntryContainsCategoryPredicate) other).category)); // state check
    }
}
