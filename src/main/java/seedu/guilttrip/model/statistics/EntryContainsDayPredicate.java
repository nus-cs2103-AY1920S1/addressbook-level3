package seedu.guilttrip.model.statistics;

import java.util.function.Predicate;

import seedu.guilttrip.model.entry.Entry;

/**
 * Filters the entrylist predicates based on the specific date.
 */
public class EntryContainsDayPredicate implements Predicate<Entry> {
    private int monthFromUser;
    private int yearFromUser;
    private int dailyFromUser;

    /**
     * Filters the entrylist based on the LocalDate which comprises of dailyFromUser
     * and monthFromUser and int yearFromUser.
     */
    public EntryContainsDayPredicate(int dailyFromUser, int monthFromUser, int yearFromUser) {
        this.dailyFromUser = dailyFromUser;
        this.monthFromUser = monthFromUser;
        this.yearFromUser = yearFromUser;
    }

    @Override
    public boolean test(Entry entry) {
        return this.dailyFromUser == (entry.getDate().getDate().getDayOfMonth())
                && monthFromUser == (entry.getDate().getDate().getMonth().getValue())
                && yearFromUser == (entry.getDate().getDate().getYear());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsDayPredicate // instanceof handles nulls
                && monthFromUser == ((((EntryContainsDayPredicate) other).monthFromUser))
                && yearFromUser == ((((EntryContainsDayPredicate) other).yearFromUser))); // state check
    }
}

