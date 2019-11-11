package seedu.guilttrip.model.statistics;

import java.util.function.Predicate;

import seedu.guilttrip.model.entry.Entry;


/**
 * Filters the entrylist predicates based on the int month and int year. Identifies the entries by the month.
 */
public class EntryContainsMonthYearPredicate implements Predicate<Entry> {
    private int monthFromUser;
    private int yearFromUser;

    /**
     * Filters the entrylist based on the int monthFromUser and int yearFromUser.
     */
    public EntryContainsMonthYearPredicate(int monthFromUser, int yearFromUser) {
        this.monthFromUser = monthFromUser;
        this.yearFromUser = yearFromUser;
    }

    @Override
    public boolean test(Entry entry) {
        return monthFromUser == (entry.getDate().getDate().getMonth().getValue())
                && yearFromUser == (entry.getDate().getDate().getYear());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsMonthYearPredicate // instanceof handles nulls
                && monthFromUser == ((((EntryContainsMonthYearPredicate) other).monthFromUser))
                && yearFromUser == ((((EntryContainsMonthYearPredicate) other).yearFromUser))); // state check
    }
}
