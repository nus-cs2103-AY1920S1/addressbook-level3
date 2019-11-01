package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Entry;

/**
 * Tests that a {@code Entry}'s {@code Category} is larger than the given category.
 */
public class EntryContainsCategoryPredicate implements Predicate<Entry> {
    private final String categoryName;

    public EntryContainsCategoryPredicate(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean test(Entry entry) {
        return categoryName.equalsIgnoreCase(entry.getCategory().getCategoryName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsCategoryPredicate // instanceof handles nulls
                && categoryName == (((EntryContainsCategoryPredicate) other).categoryName)); // state check
    }
}
