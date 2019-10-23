package seedu.address.model.statistics;

import java.util.function.Predicate;

import seedu.address.model.person.Entry;

/**
 * Filters the entrylist predicates based on the int day.
 */
public class EntryContainsDayPredicate implements Predicate<Entry> {

    private int dayFromUser;

    /**
     * Filters the entrylist based on the int day.
     */
    public EntryContainsDayPredicate(int dayFromUser) {
        this.dayFromUser = dayFromUser;
    }

    @Override
    public boolean test(Entry entry) {
        return dayFromUser == (entry.getDate().getDate().getDayOfMonth());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsDayPredicate // instanceof handles nulls
                && dayFromUser == ((((EntryContainsDayPredicate) other).dayFromUser))); // state check
    }

}
