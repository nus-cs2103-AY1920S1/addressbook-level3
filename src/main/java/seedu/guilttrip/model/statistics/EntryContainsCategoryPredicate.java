package seedu.guilttrip.model.statistics;

import java.util.function.Predicate;

import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Entry;

/**
 * Filters out the entries that have the same category as the specified category categoryFromUser.
 */
public class EntryContainsCategoryPredicate implements Predicate<Entry> {

    private Category categoryFromUser;

    /**
     * Filters the entrylist based on the specified category.
     */
    public EntryContainsCategoryPredicate(Category categoryFromUser) {
        this.categoryFromUser = categoryFromUser;
    }

    @Override
    public boolean test(Entry entry) {
        return categoryFromUser.equals(entry.getCategory());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsCategoryPredicate // instanceof handles nulls
                && categoryFromUser.equals((((EntryContainsCategoryPredicate) other).categoryFromUser))); // state check
    }

}
