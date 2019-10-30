package seedu.address.model.statistics;

import java.util.function.Predicate;

import seedu.address.model.person.Category;
import seedu.address.model.person.Entry;

/**
 * Filters out the entries that have the same category as the specified category categoryFromUser.
 */
public class EntryContainsCategoryPredicate implements Predicate<Entry> {

    private Category categoryFromUser;

    /**
     * Filters the entrylist based on the int day.
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
