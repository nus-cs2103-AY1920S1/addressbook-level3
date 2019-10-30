package seedu.address.model.person.predicates;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.address.model.person.Entry;

/**
 * Tests that a {@code Entry's}'s {@code LocalDate} matches any of the keywords given.
 */
public class EntryContainsDatePredicate implements Predicate<Entry> {
    private final LocalDate dateToFilter;

    public EntryContainsDatePredicate(LocalDate dateToFilter) {
        this.dateToFilter = dateToFilter;
    }

    @Override
    public boolean test(Entry entry) {
        return dateToFilter.equals(entry.getDate().getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsDatePredicate // instanceof handles nulls
                && dateToFilter == (((EntryContainsDatePredicate) other).dateToFilter)); // state check
    }
}
