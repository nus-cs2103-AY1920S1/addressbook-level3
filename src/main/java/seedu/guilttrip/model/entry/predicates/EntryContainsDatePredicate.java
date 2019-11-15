package seedu.guilttrip.model.entry.predicates;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.guilttrip.model.entry.Entry;

/**
 * Tests that a {@code Entry's}'s {@code LocalDate} matches any of the keywords given.
 */
public class EntryContainsDatePredicate implements Predicate<Entry> {
    private final LocalDate dateToFilter;
    private final int month;
    private final int year;

    public EntryContainsDatePredicate(LocalDate dateToFilter) {
        this.dateToFilter = dateToFilter;
        this.month = dateToFilter.getMonthValue();
        this.year = dateToFilter.getYear();
    }

    @Override
    public boolean test(Entry entry) {
        return this.month == (entry.getDate().getDate().getMonthValue())
                && this.year == (entry.getDate().getDate().getYear());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsDatePredicate // instanceof handles nulls
                && month == (((EntryContainsDatePredicate) other).month) // state check
                && year == (((EntryContainsDatePredicate) other).year));
    }
}
